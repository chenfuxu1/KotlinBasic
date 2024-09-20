package com.chapter06.delegate

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 21:51
 *
 * 接口实现类
 **/
class ApiImpl: Api {
    override fun a() {
        println("a...")
    }

    override fun b() {
        println("b...")
    }

    override fun c() {
        println("c...")
    }
}