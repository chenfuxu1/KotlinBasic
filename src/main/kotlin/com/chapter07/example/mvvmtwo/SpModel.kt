package com.chapter07.example.mvvmtwo

import com.chapter07.example.mvvmtwo.Models.register

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:37
 *
 * Desc:
 */
class SpModel : AbsModel() {
    init {
        // 自己传入名称
        Models.run {
            register("SpModel2")
        }
    }

    fun hello(content: String) = "hello world"
}