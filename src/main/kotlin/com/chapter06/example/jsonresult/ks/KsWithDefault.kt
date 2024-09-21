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

    /**
     * 2.测试带默认值情况
     * 支持默认值序列化
     */
    println(json.encodeToString(com.chapter06.example.jsonresult.bean.PersonWithDefault("杨过"))) // {"name":"杨过","age":200}
    println(Json.encodeToString(com.chapter06.example.jsonresult.bean.PersonWithDefault("杨过"))) // {"name":"杨过"} 默认值未被序列化
    val decodeFromString2 = json.decodeFromString<com.chapter06.example.jsonresult.bean.PersonWithDefault>("""{"name":"小龙女"}""") // PersonWithDefault(name=小龙女, age=200)
    println(decodeFromString2)
}