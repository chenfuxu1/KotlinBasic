package com.chapter13.callbacktosuspend

import com.utils.Logit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 23:23
 *
 * Desc: 回调函数转挂起函数
 */
fun interface SingleMethodCallback {
    fun onCallback(value: String)
}

fun runTask(callback: SingleMethodCallback) {
    thread {
        Thread.sleep(1000)
        callback.onCallback("runTask result")
    }
}

suspend fun runTaskSuspend() = suspendCoroutine<String> { continuation ->
    runTask {
        continuation.resume(it)
    }
}

// fun main() {
//     // 1.回调的形式进行调用
//     runTask(object : SingleMethodCallback {
//         override fun onCallback(value: String) {
//             Logit.d("onCallback value: $value")
//         }
//
//     })
// }

suspend fun main() {
    // 2.回调的形式转挂起函数
    Logit.d(runTaskSuspend())
}