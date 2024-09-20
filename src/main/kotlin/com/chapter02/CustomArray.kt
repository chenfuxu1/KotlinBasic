package com.chapter02

/**
 * 数组
 */
fun main() {
    // 1.使用数组
    var riversArray = arrayOf("Nile", "Amazon", "Yangtze")
    riversArray += "zhangsan"
    println(riversArray.joinToString())

    // 2.创建数组
    val simpleArray = arrayOf(1, 2, 3)
    println(simpleArray.joinToString())

    // 3.创建 3 个空元素数组
    val nullArray: Array<Int?> = arrayOfNulls(3)
    println(nullArray.joinToString()) // null, null, null

    // 4.初始化 3 个为 0 的数组
    val initArray = Array<Int>(3) { 0 }
    println(initArray.joinToString()) // 0, 0, 0

    // 5.创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
    val asc = Array(5) { i ->
        (i * i).toString()
    }
    println(asc.joinToString()) // 0, 1, 4, 9, 16

    // 嵌套数组
    val twoDArray = Array(2) { Array(2) { 0 } }
    println(twoDArray.contentDeepToString()) // [[0, 0], [0, 0]]

    val threeDArray = Array(3) { Array(3) { Array<Int>(3){ 1 } } }
    // [[[1, 1, 1], [1, 1, 1], [1, 1, 1]], [[1, 1, 1], [1, 1, 1], [1, 1, 1]], [[1, 1, 1], [1, 1, 1], [1, 1, 1]]]
    println(threeDArray.contentDeepToString())

    // 向函数传入可变数量的实参
    val lettersArray = arrayOf("c", "d")
    printAllStrings("a", "b", *lettersArray) // abcd
    println()

    // 比较数组，比较两个数组的所在顺序的元素是否相同 .contentEquals()
    val simpleArray1 = arrayOf(1, 2, 3)
    val anotherArray1 = arrayOf(1, 2, 3)
    println(simpleArray1.contentEquals(anotherArray1)) // true

    // 转换数组
    // 1.sum() 求和
    val sumArray = arrayOf(1, 2, 3)
    println(sumArray.sum()) // 1 + 2 + 3

    // 2.shuffle 随机打乱数组元素位置
    val shuffleArray = arrayOf(1, 2, 3)
    shuffleArray.shuffle()
    println(shuffleArray.joinToString())
    shuffleArray.shuffle()
    println(shuffleArray.joinToString())

    // 数组转集合
    val simpleArray2 = arrayOf("a", "b", "c", "c")

    // 1.数组转为 set 集合
    println(simpleArray2.toSet()) // [a, b, c]

    // 2.数组转为 list 集合
    println(simpleArray2.toList()) // [a, b, c, c]

    // 3.数组转为 map
    val pairArray = arrayOf("apple" to 120, "banana" to 150, "cherry" to 90, "apple" to 140)
    println(pairArray.toMap()) // {apple=140, banana=150, cherry=90}

    // 原生类型数组
    val exampleArray = IntArray(5)
    println(exampleArray.joinToString()) // 0, 0, 0, 0, 0
}

fun printAllStrings(vararg strings: String) {
    for (string in strings) {
        print(string)
    }
}