package com.chapter06.example

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 10:28
 *
 * Desc: 递归整型列表
 */
sealed class IntList {
    object Nil : IntList() { // 最后一位存储一个空对象
        override fun toString(): String {
            return "Nil"
        }
    }

    data class Cons(val head: Int, val tail: IntList, val first: Boolean = false) : IntList() {
        override fun toString(): String {
            return "$head, $tail"
        }
    }

    fun joinToString(sep: Char = ','): String {
        return when (this) {
            Nil -> "]"
            is Cons -> {
                if (first) {
                    "[$head$sep ${tail.joinToString(sep)}"
                } else {
                    return when (tail) {
                        Nil -> "$head${tail.joinToString(sep)}"
                        else -> {
                            "$head$sep ${tail.joinToString(sep)}"
                        }
                    }
                }
            }
        }
    }
}

// 求和
fun IntList.sum(): Int {
    return when(this) {
        IntList.Nil -> 0
        is IntList.Cons -> {
            head + tail.sum()
        }
    }
}

fun intListOf(vararg ints: Int, first: Boolean = true): IntList {
    return when (ints.size) {
        0 -> IntList.Nil
        else -> {
            IntList.Cons(
                ints[0],
                intListOf(*(ints.slice(1 until ints.size).toIntArray()), first = false), // 加 * 号可以将数组转成可变参数一个一个传入
                first
            )
        }
    }
}

/**
 * 创建 [0, 1, 2, 3]
 * 
 */
fun main() {
    // val list = IntList.Cons(0, IntList.Cons(1, IntList.Cons(2, IntList.Cons(3, IntList.Nil))))
    val list = intListOf(0, 1, 2, 3)
    println(list)
    println(list.joinToString()) // [0, 1, 2, 3]
    println(list.sum())
}