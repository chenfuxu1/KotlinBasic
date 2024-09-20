package com.chapter06.enumclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-13 23:35
 *
 * Desc: 枚举类
 */
enum class State {
    Idle, Busy
}

fun main() {
    // Idle
    println(State.Idle.name)
    // 0
    println(State.Idle.ordinal)
}