package com.chapter06.example.jsonresult.gson

import com.chapter06.example.jsonresult.bean.Person
import com.google.gson.Gson

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-20 23:44
 *
 * Desc: 测试 Gson 序列化
 */
fun main() {
    val gson = Gson()
    // 1.测试不带默认值的情况
    // {"name":"张三","age":110}
    println(gson.toJson(com.chapter06.example.jsonresult.bean.Person("张三", 110)))
    // Person(name=李四, age=220)
    println(gson.fromJson("""{"name":"李四","age":220}""", com.chapter06.example.jsonresult.bean.Person::class.java))
}