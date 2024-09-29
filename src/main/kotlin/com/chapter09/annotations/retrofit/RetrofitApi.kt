package com.chapter09.annotations.retrofit

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Proxy
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-28 23:35
 *
 * Desc:
 */
object RetrofitApi {
    const val PATH_PATTERN = """(\{(\w+)\})"""

    val okHttp = OkHttpClient()
    val gson = Gson()

    /**
     * 拿到外部类的 api 接口参数
     */
    val enclosing = { cls: Class<*> ->
        var currentCls: Class<*>? = cls
        sequence {
            while (currentCls != null) {
                // yield(currentCls)
                // currentCls = currentCls?.enclosingClass

                currentCls = currentCls?.also { yield(it) }?.enclosingClass
            }
        }
    }

    inline fun <reified T> create(): T {
        val functionMap = T::class.functions.map { it.name to it}.toMap()
        val interfaces = enclosing(T::class.java).takeWhile { it.isInterface }.toList()
        // 因为获取的接口列表是从内到外的，所以拼接需要从右往左拼接
        val apiPath = interfaces.foldRight(StringBuffer()) { clazz, acc ->
            acc.append(clazz.getAnnotation(Api::class.java)
                ?.url?.takeIf { it.isNotEmpty() }
                ?: clazz.name).append("/")
        }.toString()

        return Proxy.newProxyInstance(RetrofitApi.javaClass.classLoader, arrayOf(T::class.java)) { proxy, method, args ->
            functionMap[method.name]?.takeIf { it.isAbstract }?.let { function ->
                val parameterMap = function.valueParameters.map {
                    it.name to args[it.index - 1] // valueParameters 中包含 receiver 因此需要 index - 1 来对应 args
                }.toMap()
                // 拿到 Get 注解的值，例如：{name}
                val endPoint = function.findAnnotation<Get>()!!.url.takeIf { it.isNotEmpty() } ?: function.name
                val compiledEndPoint = Regex(PATH_PATTERN).findAll(endPoint).map { matchResult ->
                    matchResult.groups[1]!!.range to parameterMap[matchResult.groups[2]!!.value]
                }.fold(endPoint) { acc, pair ->
                    acc.replaceRange(pair.first, pair.second.toString())
                }
                val url = apiPath + compiledEndPoint
                println(url)

                okHttp.newCall(Request.Builder().url(url).get().build()).execute().body()?.charStream()?.use {
                    gson.fromJson(JsonReader(it), method.genericReturnType)
                }
            }
        } as T
    }
}

fun main() {
    val usersApi = RetrofitApi.create<GithubApi.Users>()
    println(usersApi.get("chenfuxu1"))
    println(usersApi.followers("chenfuxu1").map { it.login })
}