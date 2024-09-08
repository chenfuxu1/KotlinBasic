package chapter05

import java.io.File


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 15:48
 *
 * 常用的内联函数
 * 1.let / run: 返回 lambda 的结果
 * 2.also / apply: 返回对象本身 receiver
 * 3.use：自动关闭资源
 **/
fun main() {
    val person = Person("张三", 100)

    /**
     * public inline fun <T, R> T.let(block: (T) -> R): R {
     *
     * }
     */
    person.let {
        // it 表示就是 person 对象本身
    }

    /**
     * public inline fun <T, R> T.run(block: T.() -> R): R {
     *
     * }
     */
    person.run {
        // 需要一个 receiver
    }

    person.let(::println)
    person.run(::println)

    /**
     * public inline fun <T> T.also(block: (T) -> Unit): T {
     *
     * }
     * 返回对象本身
     */
    val person2 = person.also {
        it.name = "李四"
    }
    person2.let(::println)

    /**
     * public inline fun <T> T.apply(block: T.() -> Unit): T {
     *
     * }
     * { } 内直接就是 person 的内部
     * 因为接收的是 T.()，一个 receiver
     * 也会返回对象本身
     */
    val person3 = person.apply {
        name = "王五"
    }

    /**
     * use 函数会对每个 Closeable 进行一系列的异常处理、资源关闭操作
     * public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
     *     contract {
     *         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
     *     }
     *     var exception: Throwable? = null
     *     try {
     *         return block(this)
     *     } catch (e: Throwable) {
     *         exception = e
     *         throw e
     *     } finally {
     *         when {
     *             apiVersionIsAtLeast(1, 1, 0) -> this.closeFinally(exception)
     *             this == null -> {}
     *             exception == null -> close()
     *             else ->
     *                 try {
     *                     close()
     *                 } catch (closeException: Throwable) {
     *                     // cause.addSuppressed(closeException) // ignored here
     *                 }
     *         }
     *     }
     * }
     */
    File("build.gradle").inputStream().reader().buffered()
        .use {
            println(it.readLines())
        }
    // 执行到此处会关闭所有的资源
}

class Person(var name: String, var age: Int) {

}