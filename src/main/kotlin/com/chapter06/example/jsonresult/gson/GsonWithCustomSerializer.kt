package com.chapter06.example.jsonresult.gson

import com.google.gson.GsonBuilder
import java.util.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 0:04
 *
 * Desc:
 */
fun main() {
    val gson = GsonBuilder()
        .registerTypeAdapter(
            Date::class.java,
            Serializers.DateSerializer
        )
        .registerTypeAdapter(
            Date::class.java,
            Serializers.DateDeserializer
        )
        .create()
    // {"name":"Zhang San","age":20,"birthDate":"2024-09-21 00:09"}
    println(gson.toJson(PersonWithDate("Zhang San", 20, Date())))
    // PersonWithDate(name=Li Si, age=20, birthDate=Sun Oct 13 15:58:00 CST 2019)
    println(gson.fromJson("""{"name":"Li Si","age":20,"birthDate":"2019-10-13 15:58"}""", PersonWithDate::class.java))
}