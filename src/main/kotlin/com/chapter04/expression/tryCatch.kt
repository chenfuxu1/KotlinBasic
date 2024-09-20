package com.chapter04.expression

/**
 * 异常
 */
fun main() {
    // 1.使用 throw 表达式来抛出异常
    throw Exception("抛异常")

    // 2.使用 try……catch 表达式来捕获异常
    try {
        // 一些代码
    } catch (e: Exception) {
        // 处理程序
    } finally {
        // 可选的 finally 块
    }
}