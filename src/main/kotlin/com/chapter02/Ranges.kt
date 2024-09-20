package com.chapter02

/**
 * 区间
 */
fun main() {
    // 1.区间的创建
    val intRange = 1 .. 10 // [1, 10]
    val charRange = 'a' .. 'z'
    val longRange = 1L .. 100L
    println("intRange：${intRange.joinToString()}")
    println("charRange：${charRange.joinToString()}")
    println("longRange：${longRange.joinToString()}")

    val floatRange = 1f .. 2f // 连续值 [1, 2]
    val doubleRange = 1.0 .. 2.0 // 连续值 [1, 2]
    println("floatRange：${floatRange.contains(1f)}")
    println("doubleRange：${doubleRange}")

    val uintRange = 1U .. 10U
    val ulongRange = 1UL .. 10UL
    println("uintRange：${uintRange.joinToString()}")
    println("ulongRange：${ulongRange.joinToString()}")

    // 2.开区间
    val intRangeExclusive = 1 until 10 // [1, 10)
    val charRangeExclusive = 'a' until 'z'
    val longRangeExclusive = 1L until 100L
    println("=================")
    println("intRangeExclusive：${intRangeExclusive.joinToString()}")
    println("charRangeExclusive：${charRangeExclusive.joinToString()}")
    println("longRangeExclusive：${longRangeExclusive.joinToString()}")

    // 3.倒序区间
    val intRangeReverse = 10 downTo  1 // [10, 9, ... 1]
    val charRangeReverse = 'z' downTo  'a'
    val longRangeReverse = 100L downTo  1L
    println("=================")
    println("intRangeReverse：${intRangeReverse.joinToString()}")
    println("charRangeReverse：${charRangeReverse.joinToString()}")
    println("longRangeReverse：${longRangeReverse.joinToString()}")

    // 4.区间的步长
    val intRangeWithStep = 1 .. 10 step 2 // [1, 3, 5, 7, 9]
    val charRangeWithStep = 'a' .. 'z' step 2
    val longRangeWithStep = 1L .. 100L step 2
    println("=================")
    println("intRangeWithStep：${intRangeWithStep.joinToString()}")
    println("charRangeWithStep：${charRangeWithStep.joinToString()}")
    println("longRangeWithStep：${longRangeWithStep.joinToString()}")

    /**
     * 5.区间的包含关系
     * 对于离散值或连续值都可以用 in / !in
     */
    if (2.0 in doubleRange) {
        println("2.0 is in doubleRange")
    }

    if (12 !in intRange) {
        println("12 is not in intRange")
    }
    println("=================")

    // 区间的应用
    val array = intArrayOf(1, 3, 5, 7, 8)
    for (i in 0 until array.size) { // 遍历
        print("${array[i]} \t")
    }
    // 等价写法
    println("=================")
    for (i in array.indices) { // array.indices 返回的就是数组的索引区间 0 until array.size
        print("${array[i]} \t")
    }
}