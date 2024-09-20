package com.chapter02

/**
 * 计算器
 * input：3 * 4
 */
fun main(vararg args: String) {
    if (args.size < 3) {
        return showHelp()
    }

    val operators = mapOf(
        "+" to ::plus,
        "-" to ::minus,
        "*" to ::multi,
        "/" to ::div
    )

    val operator = args[1]
    val operatorFunc = operators[operator] ?: return showHelp()

    try {
        println("Input: ${args.joinToString(" ")}")
        println("Output: ${operatorFunc(args[0].toInt(), args[2].toInt())}")
    } catch (e: Exception) {
        println("Invalid parameters")
    }
}

fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun multi(num1: Int, num2: Int): Int {
    return num1 * num2
}

fun div(num1: Int, num2: Int): Int {
    return num1 / num2
}


fun showHelp() {
    println("""
        Simple Calculator
        Input 3 * 4
        Output 12
    """.trimIndent())
}
