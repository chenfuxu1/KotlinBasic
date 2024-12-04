package com.chapter13.callbacktosuspend

import com.utils.Logit
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 23:43
 *
 * Desc:
 */
fun interface Cancellable {
    fun cancel()
}

fun sendNetworkRequestCancellable(callback: SuccessOrFailureCallback): Cancellable {
    val thread = thread {
        try {
            Thread.sleep(1000)
            callback.onSuccess("success")
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
    return Cancellable {
        thread.interrupt()
    }
}

// 转换为挂起函数 suspendCancellableCoroutine 才有 invokeOnCancellation
suspend fun sendNetworkRequestSuspendCancellable() = suspendCancellableCoroutine<String> { continuation ->
    val cancellable = sendNetworkRequestCancellable(object : SuccessOrFailureCallback {
        override fun onSuccess(value: String) {
            // 正常返回
            continuation.resume(value)
        }

        override fun onError(error: Throwable) {
            // 异常
            continuation.resumeWithException(error)
        }
    })
    continuation.invokeOnCancellation {
        // 取消
        cancellable.cancel()
    }
}

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        try {
            Logit.d(sendNetworkRequestSuspendCancellable())
        } catch (e: Exception) {
            Logit.d("sendNetworkRequestSuspendCancellable error: $e")
        }
    }
    delay(100)
    scope.cancel()
}