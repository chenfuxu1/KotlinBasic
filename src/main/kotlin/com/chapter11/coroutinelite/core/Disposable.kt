// package com.chapter11.coroutinelite.core
//
// import com.chapter11.coroutinelite.Job
// import com.chapter11.coroutinelite.OnCancel
//
// /**
//  * Project: KotlinBasic
//  * Create By: Chen.F.X
//  * DateTime: 2024-11-04 23:52
//  *
//  * Desc:
//  */
// typealias OnCompleteT<T> = (Result<T>) -> Unit
//
// interface Disposable {
//     fun dispose()
// }
//
// class CompletionHandlerDisposable<T>(val job: Job, val onComplete: OnCompleteT<T>) : Disposable {
//     override fun dispose() {
//         job.remove(this)
//     }
// }
//
// class CancellationHandlerDisposable(val job: Job, val onCancel: OnCancel) : Disposable {
//     override fun dispose() {
//         job.remove(this)
//     }
// }
//
// /**
//  * 创建存储 Disposable 的递归列表
//  * 例如：1, 2, 3, 4 会存储为 head：1，tail：2, 3, 4 -> tail 的 head：2，tail 的 tail 为 3, 4
//  * DisposableList.Cons(0, DisposableList.Cons(1, DisposableList.Cons(2, DisposableList.Cons(3, IntList.Nil))))
//  */
// sealed class DisposableList {
//     object Nil : DisposableList() // 空的列表
//
//     // 头元素 + DisposableList 列表
//     class Cons(val head: Disposable, val tail: DisposableList) : DisposableList()
// }
//
// /**
//  * DisposableList 的移除扩展方法
//  */
// fun DisposableList.remove(disposable: Disposable): DisposableList {
//     return when (this) {
//         DisposableList.Nil -> this // 是空的，直接返回
//         is DisposableList.Cons -> {
//             if (head == disposable) { // 如果是头结点元素，直接返回 tail 就是移除后的列表
//                 return tail
//             } else { // 如果不是头结点元素，继续递归到 tail 里面移除
//                 DisposableList.Cons(head, tail.remove(disposable))
//             }
//         }
//     }
// }
//
// /**
//  * tailrec 是 Kotlin 中的关键字，它用于确保递归函数的最后一个操作是递归调用。如果不是，编译器会报错。这样可以避免栈溢出错误
//  * DisposableList 遍历的扩展方法
//  */
// tailrec fun DisposableList.forEach(action: (Disposable) -> Unit): Unit = when (this) {
//     DisposableList.Nil -> Unit
//     is DisposableList.Cons -> {
//         action(this.head)
//         this.tail.forEach(action)
//     }
// }
//
// inline fun <reified T : Disposable> DisposableList.loopOn(crossinline action: (T) -> Unit) = forEach {
//     when (it) {
//         is T -> action(it)
//     }
// }