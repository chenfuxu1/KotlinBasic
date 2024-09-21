package com.chapter06.example.jsonresult.ks

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 17:33
 *
 * Kotlinx.serialization
 **/
fun main() {
    // {"name":"Zhang San","age":20,"birthDate":"2024-09-21 00:59"}
    println(Json.encodeToString(PersonWithDate("Zhang San", 20, Date())))
    // PersonWithDate(name=Li Si, age=20, birthDate=Sun Oct 13 15:58:00 CST 2019)
    println(Json.decodeFromString<PersonWithDate>("""{"name":"Li Si","age":20,"birthDate":"2019-10-13 15:58"}"""))
}