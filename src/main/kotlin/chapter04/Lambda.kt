package chapter04

fun func() {
    println("一个普通函数")
}

val text = fun() {
    println("一个匿名函数")
}

/**
 * 匿名函数的类型
 * 这里的类型是 () -> Unit
 * 表示入参为 0 个参数，返回值为 Unit
 */
val func2: () -> Unit = fun() {
    println("匿名函数的类型")
}

/**
 * 等价于
 * val lambda: () -> Unit = {
 *    println("lambda 的类型")
 * }
 */
val lambda = {
    println("lambda 的类型")
}

val f1: (Int) -> Unit = { p: Int ->
    println(p)
}

/**
 * f1 等价写法
 * it 的类型由表达式推断得出
 */
val f2: (Int) -> Unit = { it ->
    println(it)
}

// f2 的等价写法
val f3: Function1<Int, Unit> = {
    println(it)
}

// f1 等价写法，表达式的类型由声明推断而来
val f4 = { p: Int ->
    println(p)
}

// lambda 表达式最后一行是返回值类型
val f5 = { p: Int ->
    println(p)
    "返回值类型"
}