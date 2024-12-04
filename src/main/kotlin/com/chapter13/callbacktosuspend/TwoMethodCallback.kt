package com.chapter13.callbacktosuspend

import com.utils.Logit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 23:32
 *
 * Desc: 回调函数转挂起函数
 */
interface SuccessOrFailureCallback {
    fun onSuccess(value: String)

    fun onError(error: Throwable)
}

fun sendNetworkRequest(callback: SuccessOrFailureCallback) {
    thread {
        try {
            Thread.sleep(1000)
            callback.onSuccess("success")
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}

// 转换为挂起函数
suspend fun sendNetworkRequestSuspend() = suspendCoroutine<String> { continuation ->
    sendNetworkRequest(object : SuccessOrFailureCallback {
        override fun onSuccess(value: String) {
            // 正常返回
            continuation.resume(value)
        }

        override fun onError(error: Throwable) {
            // 异常
            continuation.resumeWithException(error)
        }
    })
}

suspend fun main() {
    try {
        Logit.d(sendNetworkRequestSuspend())
    } catch (e: Exception) {
        Logit.d("send request error: $e")
    }
}