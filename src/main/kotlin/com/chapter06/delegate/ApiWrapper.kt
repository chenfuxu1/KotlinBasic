package com.chapter06.delegate

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 21:52
 *
 * API 的接口代理 ApiWrapper
 * 可以在代理类中增强功能
 * 其中：Api by api 表示对象 api 代替类 ApiWrapper 实现接口 Api
 **/
class ApiWrapper(val api: Api): Api by api {
    override fun c() {
        println("ApiWrapper c...")
        api.c()
    }

    fun d() {
        println("ApiWrapper d...")
    }
}