// package com.chapter11.coroutinelite.sample
//
// import com.chapter11.coroutinelite.launch
// import com.utils.Logit
// import kotlin.concurrent.thread
// import kotlin.coroutines.resume
// import kotlin.coroutines.suspendCoroutine
//
// /**
//  * Project: KotlinBasic
//  * Create By: Chen.F.X
//  * DateTime: 2024-11-07 0:22
//  *
//  * Desc:
//  */
// suspend fun main() {
//     val job = launch {
//         Logit.d(1)
//         val result = hello()
//         Logit.d("2 $result")
//     }
//     Logit.d(job.isActive)
//     job.join()
//     Logit.d("hahah")
// }
//
// suspend fun hello() = suspendCoroutine<Int> {
//     thread {
//         Thread.sleep(1000)
//         it.resume(10086)
//     }
// }