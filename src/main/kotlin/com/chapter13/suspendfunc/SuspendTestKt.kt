package com.chapter13.suspendfunc

import kotlinx.coroutines.delay

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 17:23
 *
 * Desc:
 */
object SuspendTestKt {
    /**
     * 如果不是挂起函数，直接返回结果
     * 如果是挂起函数，先返回 COROUTINE_SUSPENDED 标记
     */
    suspend fun suspendableApi(): String {
        var data = "直接返回"
        if (Math.random() > 0.5) {
            delay(1000)
            data = "挂起后返回"
        }
        return data
    }

    // suspend fun suspendableApi(continuation: Continuation): Any? {}
}