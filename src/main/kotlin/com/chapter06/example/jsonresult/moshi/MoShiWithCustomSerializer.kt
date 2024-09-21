package com.chapter06.example.jsonresult.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:21
 *
 * 测试 MoShi 序列化
 **/
fun main() {
    val moShi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(DateSerializer).build()
    val jsonAdapter = moShi.adapter(PersonWithDate::class.java)
    // {"name":"Huo Ling","age":111,"birthDate":"2024-09-21 00:32"}
    println(jsonAdapter.toJson(PersonWithDate("Huo Ling", 111, Date())))
    val personWithDate = jsonAdapter.fromJson("""{"name":"Qing Yi", "age":222, "birthDate":"2019-10-13 15:58"}""")
    // PersonWithDate(name=Qing Yi, age=222, birthDate=Sun Oct 13 15:58:00 CST 2019)
    println(personWithDate)

}