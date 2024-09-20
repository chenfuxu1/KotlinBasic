package com.chapter02

/**
 * 字符串
 */
fun main() {
    val str = "abcd"
    // 1.可以使用 for 循环遍历这些字符
    for (c in str) {
        println(c)
    }

    // 2.如需连接字符串，可以用 + 操作符。这也适用于连接字符串与其他类型的值
    // 只要表达式中的第一个元素是字符串
    val s = "abc" + 1
    println(s + "def")

    // 3.字符串模版
    val i = 10
    println("i = $i") // i = 10

    val letters = listOf("a", "b", "c", "d", "e")
    println("letters: $letters") // letters: [a, b, c, d, e]

    // 多行字符串
    val text = """
        \n
        for (c in "foo")
            print(c)
    """
    println(text)

    /**
     * 如需删掉多行字符串中的前导空格，请使用 trimMargin() 函数
     * 默认以竖线 | 作为边界前缀，但你可以选择其他字符并作为参数传入，比如 trimMargin(">")
     */
    val text2 = """
        |Tell me and I forget.
            |Teach me and I remember.
                |Involve me and I learn.
                    |(Benjamin Franklin)
    """.trimMargin()
    println(text2)

    val text3 = """
        >Tell me and I forget.
            >Teach me and I remember.
            >Involve me and I learn.
                    >(Benjamin Franklin)
    """.trimMargin(">")
    println(text3)

    // String formatting
    val integerNumber = String.format("%07d", 31416) // 长度为 7 的整型的字符串
    println(integerNumber) // 0031416

    val floatNumber = String.format("%+.4f", 3.141592) // 正浮点型，小数位为 4 位
    println(floatNumber) // +3.1416

    val helloString = String.format("%S %S", "hello", "world")
    println(helloString) // "HELLO WORLD"

    val negativeNumberInParentheses = String.format("%(d means %1\$d", -31416)
    println(negativeNumberInParentheses) // (31416) means -31416

}