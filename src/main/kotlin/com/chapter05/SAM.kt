package com.chapter05

import JavaInterface
import java.util.concurrent.ScheduledThreadPoolExecutor

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 14:00
 *
 * SAM - Single Abstract Method
 **/
fun main() {
    val executorService = ScheduledThreadPoolExecutor(5)
    // 1.kotlin 匿名表达式
    executorService.execute(object : Runnable {
        override fun run() {
            // {println("kotlin 匿名表达式")}() inline body
            println("kotlin 匿名表达式")
        }

    })

    /**
     * 2.kotlin lambda 表达式
     * public void execute(Runnable command) {
     * }
     * kotlin 中的 lambda 和 java 中的 lambda 表达式是不一样的
     * java 中的 lambda 表达式是没有类型的
     * kotlin 中的 lambda 表达式的类型是 () -> Unit
     * 是将 () -> Unit 类型的 lambda 转换为 Runnable
     */

    executorService.execute {
        println("kotlin lambda 表达式")
    }

    // testKotlinInterface {} 接口定义在 kotlin 中是不支持的
    testJavaInterface {
        println("java 中接口，kotlin 中使用")
    }
}

fun testJavaInterface(javaInterface: JavaInterface) {
    javaInterface.run()
}

fun testKotlinInterface(kotlinInterface: KotlinInterface) {
    kotlinInterface.run()
}

public interface KotlinInterface {
    fun run()
}