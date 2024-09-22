package com.chapter08.pluginload

import com.plugin.Plugin
import java.io.File
import java.net.URLClassLoader
import java.util.Properties
import kotlin.reflect.full.primaryConstructor

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 20:58
 *
 * Desc: 插件化加载类
 */
class PluginClassLoader(private val classPath: String) : URLClassLoader(arrayOf(File(classPath).toURI().toURL())) {
    init {
        println("PluginClassLoader init @${hashCode()}")
    }

    protected fun finalize() {
        println("PluginClassLoader will be gc @${hashCode()}")
    }
}

class PluginLoader(private val classPath: String) {
    private val watcher = FileWatcher(
        File(classPath),
        onCreated = this::onFileChanged,
        onModified = this::onFileChanged,
        onDeleted = this::onFileChanged
    )

    private var classLoader: PluginClassLoader? = null
    private var plugin: Plugin? = null


    /**
     * 第一次加载，先调用 reload，然后启动监听
     */
    fun load() {
        reload()
        watcher.start()
    }

    /**
     * 重新加载
     */
    @Synchronized
    fun reload() {
        // 1.关闭资源
        plugin?.stop()
        this.plugin = null
        this.classLoader?.close()
        this.classLoader = null

        // 2.获取类加载器
        val newClassLoader = PluginClassLoader(classPath)
        // 3.获取 Properties
        val properties = newClassLoader.getResourceAsStream(Plugin.CONFIG)?.use {
            Properties().also { properties ->
                properties.load(it)
            }
        } ?: run {
            newClassLoader.close()
            return println("Cannot found config file for $classPath")
        }
        // 4.读取类名
        val className = properties[Plugin.KEY]
        // 5.创建 Plugin
        plugin = className?.let {
            // 获取 className 的类 cls
            val pluginImpClass = newClassLoader.loadClass(it.toString()) as? Class<Plugin> ?: run {
                newClassLoader.close()
                return println("Plugin Impl from $classPath: $it should be derived from Plugin.")
            }
            // 创建 className 实例对象
            pluginImpClass.kotlin.primaryConstructor?.call() ?: run {
                newClassLoader.close()
                return println("Illegal Plugin has no primaryConstructor")
            }
        }
        plugin?.start() // 插件加载
        this.classLoader = newClassLoader
        System.gc()
    }

    /**
     * 文件反生改变时，重新加载
     */
    private fun onFileChanged(file: File) {
        println("$file changed reloading...")
        reload()
    }
}

fun main() {
    arrayOf(
        "pluginone/build/libs/pluginone-1.0-SNAPSHOT.jar",
        "plugintwo/build/libs/plugintwo-1.0-SNAPSHOT.jar"
    ).map {
        PluginLoader(it).also { pluginLoader ->
            pluginLoader.load()
        }
    }
}