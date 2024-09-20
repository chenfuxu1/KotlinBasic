package com.chapter06

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 20:15
 *
 * 类构造器
 **/
fun main() {
    val str = String() // 类的实例化

    /**
     * public actual inline fun String(chars: CharArray): String = java.lang.String(chars) as String
     * String() 函数重载，返回 String 实例对象
     */
    val str2 = String(charArrayOf('1', '2'))
}