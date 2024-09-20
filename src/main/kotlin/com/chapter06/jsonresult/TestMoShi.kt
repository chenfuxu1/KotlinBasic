package com.chapter06.jsonresult

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
    val jsonAdapter = moShi.adapter(Person::class.java)
    println(jsonAdapter.toJson(Person("荒天帝", 222)))
    println(jsonAdapter.fromJson("""{"name":"云曦","age":555}"""))
}