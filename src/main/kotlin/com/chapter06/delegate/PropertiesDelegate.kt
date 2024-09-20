package com.chapter06.delegate

import com.chapter04.times
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.Properties
import kotlin.reflect.KProperty

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-10 23:49
 *
 * Desc: 使用属性代理读写 Config.properties
 */
class PropertiesDelegate(private val path: String, private val defaultValue: String = "") {
    private lateinit var url: URL

    private val properties: Properties by lazy {
        val prop = Properties()
        url = try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
            javaClass.getResource(path)
        } catch (e: Exception) {
            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
                ClassLoader.getSystemClassLoader().getResource(path)!!
            } catch (e: Exception) {
                FileInputStream(path).use {
                    prop.load(it)
                }
                URL("file://${File(path).canonicalFile}")
            }
        }
        prop
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return properties.getProperty(property.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        properties.setProperty(property.name, value)
        File(url.toURI()).outputStream().use {
            properties.store(it, "Hello")
        }
    }
}

abstract class AbsProperties(path: String) {
    protected val prop = PropertiesDelegate(path)
}

class Config : AbsProperties("Config.properties") {
    var author by prop
    var version by prop
    var desc by prop
}

fun main() {
    val config = Config()
    println(config.author)
    println(config.version)
    println(config.desc)
    println("=" * 30)
    config.author = "CFX"
    println(config.author)
}