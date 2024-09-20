package com.chapter05

import com.chapter04.times

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 23:19
 *
 * flatMap 函数
 **/
fun main() {
    val list = listOf(1, 2, 3)

    list.flatMap {
        0 until it
    }.forEach {
        println("result: $it")
    }

    println("=" * 30)
    list.asSequence()
        .flatMap {
            (0 until it).asSequence()
        }
        .joinToString()
        .let(::println)
}