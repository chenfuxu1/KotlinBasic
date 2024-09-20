package com.chapter06.enumclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-13 23:57
 *
 * Desc: 区间
 */
enum class Color {
    White, Red, Green, Blue, Yellow, Black
}

fun main() {
    val colorRange = Color.White .. Color.Blue
    val colorRed = Color.Red
    val colorBlack = Color.Black

    println(colorRed in colorRange) // true
    println(colorBlack in colorRange) // false
}