package com.chapter06.jsonresult

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 17:33
 *
 * Kotlinx.serialization
 **/
fun main() {
    println(Json.encodeToString(Person("荒天帝", 111)))
    val decodeFromString = Json.decodeFromString<Person>("""{"name":"哈哈哈","age":222}""")
    println(decodeFromString)
}