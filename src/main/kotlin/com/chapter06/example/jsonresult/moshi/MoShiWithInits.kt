package com.chapter06.example.jsonresult.moshi

import com.chapter04.times
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:21
 *
 * 测试 MoShi 序列化
 **/
fun main() {
    val moShi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moShi.adapter(PersonWithInits::class.java)
    println(jsonAdapter.toJson(PersonWithInits("Huo Ling", 111))) // {"name":"Huo Ling","age":111}
    val personWithInits = jsonAdapter.fromJson("""{"name":"Qing Yi", "age":222}""")
    println(personWithInits) // PersonWithInits(name=Qing Yi, age=222)
    println(personWithInits?.firstName) // Qing
    println(personWithInits?.lastName) // Yi

    println("=" * 30)
    val personWithInits2 = jsonAdapter.fromJson("""{"name":"Yue Chan", "age":222, "lastName":"Xian"}""")
    println(personWithInits2) // PersonWithInits(name=Yue Chan, age=222)
    println(personWithInits2?.firstName) // Yue
    println(personWithInits2?.lastName) // Chan
}