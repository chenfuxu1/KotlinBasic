package com.test

import com.chapter11.coroutinelite.delay
import com.utils.Logit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-07 17:44
 *
 * Desc:
 */
fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)
    scope.launch {
        Logit.d("start coroutine")
        val dataA = loadDataA()
        Logit.d("dataA: $dataA")
        val dataB = loadDataB(dataA)
        Logit.d("dataB；$dataB")
        Logit.d("end coroutine")
    }

    Thread.sleep(10000)
}

suspend fun loadDataA(): String {
    Logit.d("start loadDataA")
    delay(1000)
    Logit.d("end loadDataA")
    return "荒天帝"
}

suspend fun loadDataB(content: String): String {
    Logit.d("start loadDataB")
    delay(1000)
    Logit.d("end loadDataB")
    return "$content 叶天帝"
}