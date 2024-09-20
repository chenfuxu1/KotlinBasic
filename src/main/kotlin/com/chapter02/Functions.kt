package com.chapter02

/**
 * 函数
 * 有 receiver 的函数为方法
 */
fun main(vararg args: String): Unit {
    // 变长参数
    println(args.contentToString())

    /**
     * 等价写法：
     * val x: (Foo, String, Long) -> Any = Foo::bar
     * val x: Function3<Foo, String, Long, Any> = Foo::bar
     *
     * (Foo, String, Long) -> Any = Foo.(String, Long) -> Any = Function3<Foo, String, Long, Any>
     */
    val x = Foo::bar
    val y: (Foo, String, Long) -> Any = x
    val z: Function3<Foo, String, Long, Any> = x

    yy(x)

    val f: () -> Unit = ::foo
    val g: (Int) -> String = ::foo
    val h: (Foo, String, Long) -> Any = Foo::bar

    // 变长参数
    multiParameters(1, 2, 3)
    listOf(1, 2, 3, 4)

    // 多返回值
    val (a, b, c) = multiReturnValues()
    println("a: $a \tb: $b \tc: $c")

    // 默认参数
    defaultParameter(10, "荒天帝")
    defaultParameter(y = "荒天帝")
}

fun yy(p0: (Foo, String, Long) -> Any)  {
    println(p0.invoke(Foo(), "Hello", 3L))
}

class Foo {
    /**
     * 有 receiver，即为 Foo 实例
     * 所以 bar 是方法
     */
    fun bar(p0: String, p1: Long): Any {
        return p0 + p1
    }
}

/**
 * 没有 receiver，是函数
 */
fun foo() {

}

/**
 * 没有 receiver，是函数
 */
fun foo(p0: Int): String {
   return p0.toString()
}

fun multiParameters(vararg ints: Int) {
    println(ints.contentToString())
}

// 多返回值
fun multiReturnValues(): Triple<Int, Long, Double> {
    return Triple(1, 3L, 4.0)
}

/**
 * 默认参数
 */
fun defaultParameter(x: Int = 5, y: String, z: Long = 0L) {
    println("x: $x \ty: $y \tz: $z")
}