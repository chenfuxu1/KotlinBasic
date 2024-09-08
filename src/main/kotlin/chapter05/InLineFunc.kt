package chapter05

import chapter04.times

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 14:46
 *
 * 内联函数
 **/
fun main() {
    val ints = intArrayOf(1, 2, 3, 4, 5)
    /**
     * public inline fun IntArray.forEach(action: (Int) -> Unit): Unit {
     *     for (element in this) action(element)
     * }
     */
    ints.forEach {
        println("Hello $it")
    }

    /**
     * 编译器通过内联函数直接生成下面代码
     * 等价于下面写法
     */
    println("=" * 30)
    for (element in ints) {
        println("Hello $element")
    }

    /**
     * 如果不加 inline 关键字，本来只想测量 println("Hello") 的时间的
     * 现在又要创建 block，又要调用，时间肯定更长
     *
     * 加了 inline 关键字，编译器优化等价与下面写法
     * val start = System.currentTimeMillis()
     * println("Hello")
     * println("运行时间：" + (System.currentTimeMillis() - start) + " ms")
     * 优化了性能
     */
    costTime {
        println("Hello")
    }

    nonLocalReturn {
        /**
         * 会直接返回 main 函数
         * 因为是内联函数，相当于在 main 函数中直接执行了 {} 表达式的内容
         * 所以 return 直接退出 main 函数
         */
        return
    }
}

inline fun costTime(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("运行时间：" + (System.currentTimeMillis() - start) + " ms")
}

inline fun nonLocalReturn(block: () -> Unit) {
    block()
}

/**
 * 内联属性
 */
class InLineClass() {
    var pocket: Double = 5.0
    var money: Double
         inline get() = pocket
         inline set(value) {
            pocket = value
        }

    init {
        money = 5.0
    }
}