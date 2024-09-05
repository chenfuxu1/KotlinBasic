package chapter04

/**
 * 1.变量、常量、数据类型
 */
const val MAX = 200

fun main() {
    // 可变类型
    var str: String = "Hello World"
    println(str)

    // 只读变量
    val str2: String = "只读变量"
    println(str2)

    // 类型推断
    val str3: String = "类型推断"
    println(str3)

    /**
     * 位运算
     * 只适用于 Int、Long 类型
     * shl(bits) – 有符号左移
     * shr(bits) – 有符号右移
     * ushr(bits) – 无符号右移
     * and(bits) – 位与
     * or(bits) – 位或
     * xor(bits) – 位异或
     * inv() – 位非
     */
    val x = 1 shl 2 // 1 -> 100
    println(x)

    val x2 = 8 shr 1 // 1000 -> 0100
    println(x2)

    /**
     * 浮点数比较
     * 分为：静态类型作为浮点数的操作数（Double.NaN） 和 静态类型非作为浮点数的操作数 (listOf(T))
     * 当静态类型非作为浮点数的操作数时，有如下性质
     * 1.认为 NaN 与其自身相等
     * 2.认为 NaN 比包括正无穷大（POSITIVE_INFINITY）在内的任何其他元素都大
     * 3.认为 -0.0 小于 0.0
     */
    // 静态类型作为浮点数的操作数
    println(Double.NaN == Double.NaN) // false
    println(0.0 == -0.0) // true

    // 静态类型并非作为浮点数的操作数
    // 1.认为 NaN 与其自身相等
    println(listOf(Double.NaN) == listOf(Double.NaN)) // true

    // 2.认为 NaN 比包括正无穷大（POSITIVE_INFINITY）在内的任何其他元素都大
    // [-0.0, 0.0, Infinity, NaN]
    println(listOf(Double.NaN, Double.POSITIVE_INFINITY, 0.0, -0.0).sorted())

    // 3.认为 -0.0 小于 0.0
    println(listOf(0.0) == listOf(-0.0)) // false
}