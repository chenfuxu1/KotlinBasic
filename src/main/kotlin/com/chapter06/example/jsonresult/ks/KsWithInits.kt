package com.chapter06.example.jsonresult.ks

import com.chapter06.example.jsonresult.bean.Person
import com.chapter06.example.jsonresult.bean.PersonWithDefault
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
    val json = Json {
        // 默认不会序列化实体类默认值
        encodeDefaults = true
    }
    // {"name":"Yang Guo","age":20,"lastName":"Guo"}
    println(json.encodeToString(PersonWithInits("Yang Guo", 20))) // {"name":"杨过","age":200}
    val decodeFromString2 = json.decodeFromString<PersonWithInits>("""{"name":"Xiao Long", "age":300, "lastName":"Secret"}""")
    // PersonWithInits(name=Xiao Long, age=300)
    println(decodeFromString2)
    println(decodeFromString2.firstName) // Xiao
    println(decodeFromString2.lastName) // Secret
}