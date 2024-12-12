// package com.test.statemachine
//
// import com.sun.jndi.toolkit.ctx.Continuation
// import com.utils.Logit
//
//
// /**
//  * Project: KotlinBasic
//  * Create By: Chen.F.X
//  * DateTime: 2024-12-07 17:09
//  *
//  * Desc: 状态机
//  */
//
// /**
//  * 伪代码，给每个 step 加上编号
//  */
// private suspend fun loadDataAndUpdate() {
//     switch (label) {
//         case 0:
//             loadDataA()
//             break
//         case 1:
//             loadDataB(content)
//             break
//         case 2:
//             update(content)
//             break
//     }
// }
//
// private suspend fun loadDataA(): String {
//     return "CPS suspend"
// }
//
// private suspend fun loadDataB(content: String): String {
//     return "$content Style"
// }
//
// private suspend fun update(content: String) {
//     Logit.d("update: $content")
// }
//
// // 入口：loadDataAndUpdate
// fun loadDataAndUpdate(continuation: Continuation) {
//     val sm = object : CoroutineImpl(continuation) { // continuation 是父协程的续体，在当前协程启动时会触发 continuation 的 resume
//         fun resume(...) {
//             loadDataAndUpdate(this) // 续体回调入口
//         }
//     }
//     switch (sm.label) {
//         case 0:
//             sm.label = 1 // 状态控制
//             loadDataA(sm) // step1，传入续体，执行完成后调用 resume，并将结果传递下去
//             break
//         case 1:
//             val dataA = sm.result as String // 从续体中拿取上一步的结果
//             sm.label = 2 // 状态控制
//             loadDataB(dataA, sm) // step2，传入续体 & 参数，执行完成后调用 resume，并将结果传递下去
//             break
//         case 2:
//             val dataB = sm.result as String // 从续体中拿取上一步的结果
//             update(dataB, sm) // spte3
//             break
//     }
// }