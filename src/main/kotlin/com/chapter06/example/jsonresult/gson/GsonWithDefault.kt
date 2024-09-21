package com.chapter06.example.jsonresult.gson

import com.chapter06.example.jsonresult.bean.PersonWithDefault
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
    // 2.测试带默认值的情况
    // 带有默认值 {"name":"荒天帝","age":200}
    println(gson.toJson(com.chapter06.example.jsonresult.bean.PersonWithDefault("荒天帝")))
    /**
     * 反序列化 age 为 0
     * PersonWithDefault(name=火灵儿, age=0)
     */
    println(gson.fromJson("""{"name":"火灵儿"}""", com.chapter06.example.jsonresult.bean.PersonWithDefault::class.java))
}