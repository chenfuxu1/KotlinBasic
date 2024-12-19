package com.chapter11.coroutinelite.core

import com.utils.Logit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-06 23:07
 *
 * Desc: 协程的状态
 */
sealed class CoroutineState {
    private var disposableList: DisposableList = DisposableList.Nil

    // 未完成
    class InComplete: CoroutineState()

    // 完成
    class Complete<T>(val value: T? = null, val exception: Throwable? = null): CoroutineState()

    class Cancelling: CoroutineState()

    // 返回一个新的对象
    fun from(state: CoroutineState): CoroutineState {
        this.disposableList = state.disposableList
        return this
    }

    // 添加一个元素
    fun with(disposable: Disposable): CoroutineState {
        this.disposableList = DisposableList.Cons(disposable, this.disposableList)
        return this
    }

    // 移除一个元素
    fun without(disposable: Disposable): CoroutineState {
        this.disposableList = this.disposableList.remove(disposable)
        return this
    }

    // 完成后通知更新
    fun <T> notifyCompletion(result: Result<T>) {
        this.disposableList.loopOn<CompletionHandlerDisposable<T>> {
            Logit.d("cfx notifyCompletion completionHandlerDisposable: $it")
            it.onComplete(result)
        }
    }

    // 通知进行取消
    fun notifyCancellation() {
        this.disposableList.loopOn<CancellationHandlerDisposable> {
            it.onCancel
        }
    }

    // 清空
    fun clear() {
        this.disposableList = DisposableList.Nil
    }
}