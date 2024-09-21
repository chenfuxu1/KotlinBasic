package com.chapter07

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 17:09
 *
 * Desc: 星投影
 */
fun main() {
    // val queryMap: QueryMap<*, *> = QueryMap<String, *>() // * 不能作用于函数上
    // queryMap.getKey()
    // queryMap.getValue()

    val f: Function<*, *> = Function<Number, Any>()
    // f.invoke()

    if (f is Function) { // 会有类型擦除
        (f as Function<Number, Any>).invoke(1, Any())
    }

    maxOf<Int>(1, 3)

    // 特殊情况，这里可以使用
    HashMap<String, List<*>>()
}

class QueryMap<out K : CharSequence, out V : Any> {
    fun getKey(): K = TODO()

    fun getValue(): V = TODO()
}

fun <T : Comparable<T>> maxOf(a: T, b: T) : T {
    return if (a > b) a else b
}

class Function<in P1, in P2> {
    fun invoke(p1: P1, p2: P2) = Unit
}