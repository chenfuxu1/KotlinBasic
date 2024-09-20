package com.chapter06.innerclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-12 0:24
 *
 * Desc: 内部类
 */
class Outer {
    /**
     * 成员内部类，持有外部类的引用
     */
    inner class Inner {}

    /**
     * 静态内部类，不持有外部类的引用
     */
    class StaticInner {}
}

fun main() {
    /**
     * 获取成员内部类实例
     * 需要使用 Outer() 实例
     */
    val inner = Outer().Inner()

    /**
     * 获取静态内部类实例
     * 不需要使用 Outer() 实例，直接通过 Outer 类名
     */
    val staticInner = Outer.StaticInner()
}