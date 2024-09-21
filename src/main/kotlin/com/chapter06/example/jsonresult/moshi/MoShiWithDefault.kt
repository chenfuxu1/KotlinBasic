package com.chapter06.example.jsonresult.moshi

import com.chapter06.example.jsonresult.bean.PersonWithDefault
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:21
 *
 * 测试 MoShi 序列化
 **/
fun main() {
    val moShi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    /**
     * 2.测试带默认值的情况
     * 对默认值有感知
     */
    val jsonAdapter2 = moShi.adapter(com.chapter06.example.jsonresult.bean.PersonWithDefault::class.java)
    println(jsonAdapter2.toJson(com.chapter06.example.jsonresult.bean.PersonWithDefault("火灵儿"))) // {"name":"火灵儿","age":200}
    println(jsonAdapter2.fromJson("""{"name":"清怡"}""")) // PersonWithDefault(name=清怡, age=200)
}