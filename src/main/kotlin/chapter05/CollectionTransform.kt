package chapter05

import chapter04.times
import java.lang.StringBuilder

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 16:28
 *
 * 集合变化与序列
 **/
fun main() {
    filterDemo()
    println("=" * 30)
    filterDemo2()
    println("=" * 30)
    mapDemo()
    println("=" * 30)
    mapDemo2()
    println("=" * 30)
    sumDemo()
    println("=" * 30)
    reduceAdd()
    println("=" * 30)
    reduceComb()
    println("=" * 30)
    foldAdd()
    println("=" * 30)
    foldComb()
    println("=" * 30)
    foldStringBuilder()
    println("=" * 30)
    zipDemo()
}

fun filterDemo() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.filter {
        it % 2 == 0
    }
    println(list)
    println(result)
}

fun filterDemo2() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.asSequence().filter {
        it % 2 == 0
    }
    println(list)
    println(result.joinToString())
}

fun mapDemo() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.map {
        it * 2
    }
    println(list)
    println(result)
}

fun mapDemo2() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.asSequence().map {
        it * 2
    }
    println(list)
    println(result.joinToString())
}

fun sumDemo() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.sum()
    println("result: $result")
}

/**
 * public inline fun <S, T : S> Iterable<T>.reduce(operation: (acc: S, T) -> S): S {
 * }
 * 使用 reduce() 函数计算列表中所有数字的总和
 * reduce 和 fold 的区别是 fold 可以接收一个初始值，而 reduce 不可以
 */
fun reduceAdd() {
    val list = listOf(1, 2, 3, 4, 5)
    val sum = list.reduce { acc, i ->
        println("reduce acc: $acc, i: $i")
        acc + i
    }
    println("sum is $sum") // 15
}

// 使用 reduce() 函数计算字符串列表中所有字符串的拼接结果
fun reduceComb() {
    val strings = listOf("apple", "banana", "orange", "pear")
    val result = strings.reduce { acc, s ->
        println("reduce acc: $acc, s: $s")
        "$acc-$s"
    }
    println(result) // apple-banana-orange-pear
}

/**
 * fold 函数可以接收一个初始值 initial
 * public inline fun <T, R> Iterable<T>.fold(initial: R, operation: (acc: R, T) -> R): R {
 * }
 */
fun foldAdd() {
    val list = listOf(1, 2, 3, 4, 5)
    val result = list.fold(10) { acc, i ->
        println("acc: $acc, i: $i")
        acc + i
    }
    println("fold result is $result") // 25
}

fun foldComb() {
    val list = listOf("apple", "banana", "orange", "pear")
    val result = list.fold("Fruits: ") { acc, s ->
        println("acc: $acc, s: $s")
        "$acc-$s"
    }
    println("fold result is $result") // Fruits: -apple-banana-orange-pear
}

fun foldStringBuilder() {
    val list = listOf("apple", "banana", "orange", "pear")
    val result = list.fold(StringBuilder()) { acc, s ->
        println("acc: $acc, s: $s")
        acc.append(s)
    } // 此时返回的 result 是一个 StringBuilder
    println("fold result is $result") // applebananaorangepear
}

/**
 * public infix fun <T, R> Iterable<T>.zip(other: Iterable<R>): List<Pair<T, R>> {
 *     return zip(other) { t1, t2 -> t1 to t2 }
 * }
 */
fun zipDemo() {
    val list = listOf("kotlin", "c++", "c#", "java", "html")
    val list2 = listOf(1, 2, 3, 4, 5)
    val list3 = listOf(1, 2, 3, 4, 5, 6, 7)

    val zip1 = list.zip(list2)
    // zip1 合并：[(kotlin, 1), (c++, 2), (c#, 3), (java, 4), (html, 5)]
    println("zip1 合并：$zip1")

    // 合并后的集合以合并前最短的集合计算
    val zip2 = list.zip(list3)
    // zip2 合并：[(kotlin, 1), (c++, 2), (c#, 3), (java, 4), (html, 5)]
    println("zip2 合并：$zip2")
}
