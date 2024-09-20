package com.chapter03

/**
 * 类型检测与类型转换
 */
fun main() {
    // 1.is 与 !is 操作符
    val obj = "张三"
    if (obj !is String) { // 与 !(obj is String) 相同
        print("Not a String")
    } else {
        print(obj.length) // 2
    }
}

fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x 自动转换为字符串
    }
}