package com.chapter07

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 17:40
 *
 * Desc: 泛型的实现类型与内联特化
 */
open class Box<T>(val value: T)

class StringBox(value: String) : Box<String>(value)

fun <T : Comparable<T>> maxOf2(a: T, b: T): T {
    return if (a > b) a else b
}

fun main() {
    val max = maxOf2("Hello", "World")
}