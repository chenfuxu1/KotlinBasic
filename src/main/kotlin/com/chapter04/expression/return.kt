package com.chapter04.expression

/**
 * return 标签
 */
fun main() {
    forEachContinueLabel()
    println()

    forEachBreakLabel()
    println()

    foo()
}

/**
 * 在 forEach 中并不能直接使用 break、continue
 */
// fun testForEach() {
//     listOf(1, 3, 5, 7).forEach {
//         println("it: $it")
//         if (it == 3) break
//     }
//     println("循环外继续执行")
// }

/**
 * 使用 return 模拟 continue
 */
fun forEachContinueLabel() {
    listOf(1, 3, 5, 7).forEach abc@{
        if (it == 3) return@abc
        println("it: $it")
    }
    println("循环外继续执行")
}

/**
 * 使用 return 模拟 break
 */
fun forEachBreakLabel() {
    run def@ {
        listOf(1, 3, 5, 7).forEach {
            if (it == 3) return@def
            println("it: $it")
        }
        println("循环外继续执行")
    }

}

/**
 * return 直接会会整个函数返回
 */
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // 非局部直接返回到 foo() 的调用者
        println("it: $it")
    }
    println("this point is unreachable")
}