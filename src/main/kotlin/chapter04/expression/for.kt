package chapter04.expression

/**
 * for 循环
 */
fun main() {
    for (i in 1 .. 3) {
        println(i) // 1 2 3
    }

    for (i in 6 downTo 0 step 2) {
        println(i) // 6 4 2 0
    }

    // 索引遍历
    val array = arrayOf("a", "b", "c")
    for (i in array.indices) {
        println(array[i]) // a b c
    }

    // 用库函数 withIndex
    // index = 0 value = a index = 1 value = b index = 2 value = c
    for ((index, value) in array.withIndex()) {
        println("index = $index value = $value")
    }
}