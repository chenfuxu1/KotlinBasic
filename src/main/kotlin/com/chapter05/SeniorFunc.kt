package com.chapter05

import com.chapter04.times

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 13:19
 *
 **/
fun main() {
    intArrayOf(1, 2, 3).forEach(::println)
    // forEach 需要传入一个 (Int) -> Unit 匿名函数
    intArrayOf(1, 2, 3).forEach({
        println(it)
    })
    // 作为最后一个参数 (Int) -> Unit 可以提到括号外
    intArrayOf(1, 2, 3).forEach() {
        println(it)
    }
    // 括号中没有参数，括号可省略
    intArrayOf(1, 2, 3).forEach {
        println(it)
    }
    println("=" * 30)

    cost {
        val fibonacciNext: () -> Long = fibonacci()
        for (i in 0 .. 10) {
            println(fibonacciNext())
        }
    }
}

fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("运行时间：" + (System.currentTimeMillis() - start) + " ms")
}

fun fibonacci(): () -> Long {
    var first = 0L
    var second = 1L
    return {
        val next = first + second
        val current = first
        first = second
        second = next
        current
    }
}