package com.chapter06.innerclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-12 23:32
 *
 * Desc: 内部类测试
 */
fun main() {
    // 函数内部的函数
    fun localFunc() {
        println("函数内部的函数...")
    }

    // 本地类
    class LocalClass: Cloneable, Runnable {
        override fun run() {
            println("本地类...")
        }
    }

    /**
     * 匿名内部类，可以实现多接口，还可以继承一个父类
     * runnable 类型：Cloneable + Runnable 属于交叉类型
     */
    val runnable = object : Cloneable, Runnable {
        override fun run() {
            println("匿名内部类...")
        }
    }
}