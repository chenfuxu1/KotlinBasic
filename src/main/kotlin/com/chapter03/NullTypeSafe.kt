package com.chapter03

/**
 * 空类型安全
 */
fun main() {
    var nonNull: String = "Hello"
    // nonNull = null 报错
    val length = nonNull.length

    var nullable: String? = "Hello" // 可空类型
    /**
     * 安全访问
     */
    val lengh2 = nullable?.length
    val length3 = nullable?.length ?: 0 // elvis

    // String 是 String? 的子类
    var x: String = "Hello"
    var y: String? = "world"
    // x = y 报错 Type mismatch
    y = x // 父类引用指向子类

    var a: Int = 2
    var b: Number = 10.0
    // a = b 报错 Type mismatch
    b = a // 父类引用指向子类
}