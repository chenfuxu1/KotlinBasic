package com.chapter04

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 11:35
 *
 **/
operator fun String.minus(right: Any?): String {
    return this.replaceFirst(right.toString(), "")
}

operator fun String.times(count: Int): String {
    /**
     * separator: 表示字符连接方式
     * transform: ((T) -> CharSequence) T 表示迭代器当前值
     * public fun <T> Iterable<T>.joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((T) -> CharSequence)? = null): String {
     *     return joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
     * }
     */
    return (1 .. count).joinToString("") {
        this
    }
}

operator fun String.div(right: Any?): Int {
    val right = right.toString()
    /**
     * public fun <R> CharSequence.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false, transform: (CharSequence) -> R): List<R> {}
     * windowed 表示一个窗口，每次根据 size 的大小移动字符
     * step: 每次移动的步数
     * transform: (CharSequence) -> R) 表示移动过程中的每次的字符
     * 返回 List<Boolean> 集合
     * count：统计迭代器中为 true 的个数
     */
    var windowed = this.windowed(right.length, right.length) {
        it == right
    }
    println(windowed) // [false, false, false, false, false, false, false, false, true]
    return this.windowed(right.length, 1) {
        it == right
    }.count {
        it
    }
}

fun main() {
    val value = "HelloWorld World"
    println(value - "Hello")
    println("=" * 300)
    // println(value / 3)
    println(value / "ld")
}