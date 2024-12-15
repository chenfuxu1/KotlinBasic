package com.chapter11.coroutinelite.sample

import com.chapter11.coroutinelite.async
import com.chapter11.coroutinelite.delay
import com.utils.Logit
import java.lang.ArithmeticException

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-15 17:06
 *
 **/
suspend fun main() {
    Logit.d("cfx start")
    val deferred = async {
        Logit.d("cfx 22222")
        delay(1000)
        Logit.d("cfx 33333333")
        delay(1000)
        Logit.d("cfx 44444")
        "张三"
        // throw ArithmeticException("Div 0")
    }
    try {
        val result = deferred.await()
        Logit.d("cfx result: $result")
    } catch (e: Exception) {
        Logit.d("catch exception: $e")
    }
    delay(1000)
    Logit.d("cfx end")
}