package chapter04.expression

/**
 * 条件与循环
 */
fun main() {
    val a = 2
    val b = 3
    var max = a
    if (a < b) max = b

    if (a > b) {
        max = a
    } else {
        max = b
    }

    // 1.作为表达式
    max = if (a > b) a else b

    val maxLimit = 1
    val maxOrLimit = if (maxLimit > a) maxLimit else if (a > b) a else b
    println("max is $max")
    println("maxOrLimit is $maxOrLimit")

    // 2.if 表达式的分支可以是代码块，这种情况最后的表达式作为该块的值
    val max2 = if (a > b) {
        println("max is a")
        a + 4
    } else {
        println("max is b")
        b + 4
    }
    println(max2)
}