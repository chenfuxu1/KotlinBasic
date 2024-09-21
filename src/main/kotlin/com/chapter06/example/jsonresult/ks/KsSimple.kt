package com.chapter06.example.jsonresult.ks

import com.chapter06.example.jsonresult.bean.Person
import com.chapter06.example.jsonresult.bean.PersonWithDefault
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 0:33
 *
 * Desc: Kotlinx.serialization
 */
fun main() {
    val json = Json {
    }

    // 1.测试不带默认值的情况
    println(json.encodeToString(com.chapter06.example.jsonresult.bean.Person("荒天帝", 111)))
    val decodeFromString = json.decodeFromString<com.chapter06.example.jsonresult.bean.Person>("""{"name":"哈哈哈","age":222}""")
    println(decodeFromString)
}