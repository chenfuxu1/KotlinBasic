package com.chapter10.eg.python

import com.utils.Logit
import kotlin.coroutines.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-03 16:47
 *
 * Desc: 自定义 Python 协程的 Generator
 */
interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(private val block: suspend GeneratorScope<T>.(T) -> Unit, private val parameter: T) : Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(block, parameter)
    }

}

sealed class State {
    // 未准备好
    class NotReady(val continuation: Continuation<Unit>): State()
    // 已准备好
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T): State()
    // 执行完毕
    object Done: State()
}

/**
 * 迭代器生成类，需要实现 GeneratorScope、Iterator、Continuation
 */
class GeneratorIterator<T>(private val block: suspend GeneratorScope<T>.(T) -> Unit, override var parameter: T) : GeneratorScope<T>(), Iterator<T>, Continuation<Any?> {
    override val context: CoroutineContext = EmptyCoroutineContext // 这里暂时不需要使用

    private var state: State

    init {
        // 启动协程
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = {
            block(parameter)
        }
        /**
         * public fun <R, T> (suspend R.() -> T).createCoroutine(receiver: R, completion: Continuation<T>): Continuation<Unit> = SafeContinuation(createCoroutineUnintercepted(receiver, completion).intercepted(), COROUTINE_SUSPENDED)
         * 参数 1：receiver，这里就是 this
         * 参数 2：completion，协程执行完需要回调的对象，主要标记协程执行完毕没有，这里也是 this
         */
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start) // 未准备好
    }

    override suspend fun customYield(value: T) = suspendCoroutine<Unit> { continuation ->
        state = when (state) {
            // 如果是 NotReady，现在 yield 了，那么值来了，已经准备好了
            is State.NotReady -> {
                Logit.d("customYield value: $value continuation：${continuation.hashCode()}")
                State.Ready(continuation, value)
            }
            is State.Ready<*> -> throw IllegalStateException("Cannot yield a value while ready!")
            State.Done -> throw IllegalStateException("Cannot yield a value while done!")
        }
    }

    private fun resume() {
        when (val currentState = state) {
            // 如果是 NotReady，现在 resume 了，判断下有没有值
            is State.NotReady -> {
                Logit.d("resume currentState.continuation：${currentState.continuation.hashCode()}")
                currentState.continuation.resume(Unit)
            }
        }
    }

    override fun hasNext(): Boolean {
        Logit.d("cfx hasNext")
        resume()
        return state != State.Done
    }

    override fun next(): T {
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                Logit.d("next (currentState as State.Ready<T>).nextValue: ${(currentState as State.Ready<T>).nextValue}")
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IllegalStateException("No value left!")
        }
    }

    // 表示协程体执行完毕了
    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        Logit.d("44444")
        result.getOrThrow()
    }
}

/**
 * 定义 generator 的使用范围
 * 让 yield 只能在 GeneratorScope 范围内使用
 */
abstract class GeneratorScope<T> internal constructor() {
    protected abstract val parameter: T

    abstract suspend fun customYield(value: T)
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit) : (T) -> Generator<T> {
    return { parameter: T ->
        GeneratorImpl(block, parameter)
    }
}

fun main() {
    /**
     * 这里 nums 是 (T) -> Generator<T> 类型的函数，generator 后面的大括号是 block
     */
    val nums = generator { start: Int ->
        Logit.d("22222")

        for (i in 0 .. 5) {
            customYield(start + i)
            Thread.sleep(1000)
        }
        Logit.d("3333")
    }

    /**
     * 这里的 10 就是 parameter，会传给 GeneratorImpl，返回的 seq 就是 GeneratorImpl(block, parameter)
     * GeneratorImpl 实现了 iterator()，所以可以遍历
     */
    val seq = nums(10)
    Logit.d("111111")
    for (j in seq) {
        Logit.d(j)
    }

}