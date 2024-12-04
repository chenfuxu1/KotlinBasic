package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.dispather.Dispatcher
import com.utils.Logit
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.CoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-18 0:11
 *
 * Desc:
 */
typealias EventTask = () -> Unit

// 消息生产者
class BlockingQueueDispatcher: LinkedBlockingQueue<EventTask>(), Dispatcher {
    override fun dispatch(block: () -> Unit) {
        // 每次需要调度的时候，将产生的消息扔到 LinkedBlockingQueue 消息队列中
        offer(block)
    }
}

// 消息消费者
class BlockingCoroutine<T>(context: CoroutineContext, private val eventQueue: LinkedBlockingQueue<EventTask>): AbstractCoroutine<T>(context) {
    fun joinBlocking(): T {
        while (!isCompleted) {
            Logit.d("cfx joinBlocking")
            // 执行队列中的 block 函数
            eventQueue.take().invoke()
        }
        // 协程执行完了
        return (state.get() as CoroutineState.Complete<T>).let {
            it.value ?: throw it.exception!!
        }
    }
}