package com.chapter13.suspendfunc

import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-30 22:11
 *
 * Desc:
 */
// 1.不会真正挂起，suspend 关键字可以省略
// suspend fun noSuspend(): String {
//     return "no suspend"
// }

// 2.不会真正挂起，也没有返回值的函数，suspend 关键字可以省略
// suspend fun noSuspendEmpty() {
//     // 没有返回值
// }

/**
 * 3.会真正挂起，没有返回值的函数
 * DelayKt.delay 是一个挂起函数，它会立马返回一个值：IntrinsicsKt.COROUTINE_SUSPENDED
 * 表示该函数已被挂起，这里就直接 return 了，该函数被挂起
 * 恢复执行：在 DelayKt.delay 内部，到了指定的时间后就会调用 Contin uationImpl 这个 Callback 的 invokeSuspend
 */
// suspend fun suspendEmpty() {
//     delay(1000)
//     // println("resumed after delay")
// }

/**
 * 4.真正挂起，且有返回值
 */
// suspend fun suspendRealFunc(): String {
//     delay(1000)
//     return "111"
// }

/**
 * 5.伪挂起函数
 * 等价于 suspend fun fakeSuspend() = "张三"
 */
/*
public suspend inline fun <T> suspendCoroutine(crossinline block: (Continuation<T>) -> Unit): T {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    // 返回了 suspendCoroutineUninterceptedOrReturn，主要是执行 lambda 的内容
    return suspendCoroutineUninterceptedOrReturn { c: Continuation<T> ->
        // 1.首先将进来的 continuation 获取了拦截器版本，并包装成 SafeContinuation
        val safe = SafeContinuation(c.intercepted())
        // 2.接着执行 block，也就是 suspendCoroutine<String> 的 lambda 的内容
        // 可以看到这里直接就 continuation.resume("张三")，看下 SafeContinuation 的 resume
        block(safe)
        // 3.
        safe.getOrThrow()
    }
}

public actual override fun resumeWith(result: Result<T>) {
        while (true) { // lock-free loop
            val cur = this.result // 默认是 UNDECIDED
            when {
                // 这里将 cur 直接设置为传进来的 "张三"，然后直接返回了
                cur === UNDECIDED -> if (RESULT.compareAndSet(this, UNDECIDED, result.value)) return
                cur === COROUTINE_SUSPENDED -> if (RESULT.compareAndSet(this, COROUTINE_SUSPENDED, RESUMED)) {
                    delegate.resumeWith(result)
                    return
                }
                else -> throw IllegalStateException("Already resumed")
            }
        }
    }

internal actual fun getOrThrow(): Any? {
        // result 已设置为 "张三"
        var result = this.result // atomic read
        if (result === UNDECIDED) {
            if (RESULT.compareAndSet(this, UNDECIDED, COROUTINE_SUSPENDED)) return COROUTINE_SUSPENDED
            result = this.result // reread volatile var
        }
        return when {
            result === RESUMED -> COROUTINE_SUSPENDED // already called continuation, indicate COROUTINE_SUSPENDED upstream
            result is Result.Failure -> throw result.exception
            // 这里直接返回张三
            else -> result // either COROUTINE_SUSPENDED or data
        }
    }
    // 所以这里并没有真正的挂起，相当于直接返回了 “张三” result
 */
// suspend fun fakeSuspend() = suspendCoroutine<String> { continuation ->
//     continuation.resume("张三")
// }

/**
 * 6.伪挂起函数
 * 同上面类似，只是等待了 1s 后才执行的 continuation.resume，并没有异步
 */
// suspend fun fakeSuspend2() = suspendCoroutine<String> { continuation ->
//     Thread.sleep(1000)
//     continuation.resume("张三")
// }

/**
 * 7.真正挂起函数
 * 切线程了，safe.getOrThrow() 会先执行
 */
suspend fun realSuspend() = suspendCoroutine<String> { continuation ->
    thread {
        Thread.sleep(100)
        continuation.resume("张三")
    }
}

// suspend fun main() {
//     println("aaa")
//     suspendEmpty()
// }