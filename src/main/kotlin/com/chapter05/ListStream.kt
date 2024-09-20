package com.chapter05

import com.chapter04.times

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 22:35
 *
 * java 中流操作，在 kotlin 中的等价写法
 *
 **/
fun main() {
    val list = listOf(1, 2, 3, 4)

    /**
     * 懒序列
     */
    list.asSequence()
        .filter {
            println("filter: $it")
            it % 2 == 0
        }
        .map {
            println("map: $it")
            it * 2
        }
        .forEach {
            println("result: $it")
        }

    println("=" * 30)

    /**
     * 饿汉式
     * 会立马执行完 filter 的所有操作
     * 接着会立马执行 map 的所有操作
     * 最后一次性输出所有结果
     */
    list.filter {
        println("filter: $it")
        it % 2 == 0
        }
        .map {
            println("map: $it")
            it * 2
        }
        .forEach {
            println("result: $it")
        }
}