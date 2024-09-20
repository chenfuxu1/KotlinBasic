package com.chapter04.expression

/**
 * Break、Continue 标签
 */
fun main() {
    testBreak()
    println()

    testContinue()
    println()

    testReturn()
    println()

    testBreakLabel()
    println()

    testContinueLabel()
}

fun testBreak() {
    for (i in 1..5) {
        if (i == 3) break // 这里分别使用 break continue return
        println("i: $i")
    }
    println("循环外继续执行")
}

fun testContinue() {
    for (i in 1..5) {
        if (i == 3) continue // 这里分别使用 break continue return
        println("i: $i")
    }
    println("循环外继续执行")
}

fun testReturn() {
    for (i in 1..5) {
        if (i == 3) return // 这里分别使用 break continue return
        println("i: $i")
    }
    println("循环外继续执行")
}

/**
 * break 是直接回到 loop 点结束所有循环
 */
fun testBreakLabel() {
    loop@ for (i in 1..2) {
        println("i: $i")
        for (j in 1..5) {
            if (j == 3) break@loop // break continue
            println("j: $j")
        }
    }
    println("循环外继续执行")
}

/**
 * continue 是直接回到 loop 点接着下次循环
 */
fun testContinueLabel() {
    loop@ for (i in 1..2) {
        println("i: $i")
        for (j in 1..5) {
            if (j == 3) continue@loop // break continue
            println("j: $j")
        }
    }
    println("循环外继续执行")
}