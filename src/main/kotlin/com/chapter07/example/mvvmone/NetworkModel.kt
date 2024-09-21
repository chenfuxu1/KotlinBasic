package com.chapter07.example.mvvmone


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:38
 *
 * Desc:
 */
class NetworkModel : AbsModel() {
    fun get(url: String): String = """{"code":200}"""
}