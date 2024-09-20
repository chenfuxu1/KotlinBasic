package com.chapter06.jsonresult

import com.google.gson.Gson

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:11
 *
 * 测试 Gson 序列化
 **/
fun main() {
    val gson = Gson()
    // {"name":"张三","age":110}
    println(gson.toJson(Person("张三", 110)))
    // Person(name=李四, age=220)
    println(gson.fromJson("""{"name":"李四","age":220}""", Person::class.java))
}