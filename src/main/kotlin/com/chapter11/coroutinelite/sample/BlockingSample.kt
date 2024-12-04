package com.chapter11.coroutinelite.sample

import com.chapter11.coroutinelite.delay
import com.chapter11.coroutinelite.launch
import com.chapter11.coroutinelite.runBlocking
import com.utils.Logit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-18 0:29
 *
 * Desc:
 */
fun main() = runBlocking {
    Logit.d("cfx 111")
    val job = launch {
        Logit.d("cfx 222")
        delay(3000)
        Logit.d("cfx 333")
    }
    Logit.d("cfx 444")
    job.join()
    Logit.d("cfx 555")
    delay(100)
    Logit.d("cfx 666")

}