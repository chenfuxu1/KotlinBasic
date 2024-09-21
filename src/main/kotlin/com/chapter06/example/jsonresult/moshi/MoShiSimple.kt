package com.chapter06.example.jsonresult.moshi

import com.chapter06.example.jsonresult.bean.Person
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 0:11
 *
 * Desc: 测试 MoShi 序列化
 */
fun main() {
    val moShi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moShi.adapter(com.chapter06.example.jsonresult.bean.Person::class.java)
    // 1.测试不带默认值的情况
    println(jsonAdapter.toJson(com.chapter06.example.jsonresult.bean.Person("荒天帝", 222))) // {"name":"荒天帝","age":222}
    println(jsonAdapter.fromJson("""{"name":"云曦","age":555}""")) // Person(name=云曦, age=555)
}