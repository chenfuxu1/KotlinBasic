package com.chapter12.coroutineadvanced.channel

import com.utils.Logit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 23:21
 *
 * Desc:
 */
suspend fun main() {
    // basics()
    // iterateChannel()
    // producer()
    // consumer()
    broadcast()
}

suspend fun basics() {
    // 1.RENDEZVOUS(默认) send 后，receive 后才会再次发送
    val channel = Channel<Int>(Channel.RENDEZVOUS)

    // 2.UNLIMITED 无限容量，一直发，不会等待
    // val channel = Channel<Int>(Channel.UNLIMITED)

    // 3.CONFLATED receive 只能获取最近一次 send 的值
    // val channel = Channel<Int>(Channel.CONFLATED)

    // 4.BUFFERED 默认容量为 64
    // val channel = Channel<Int>(Channel.BUFFERED)

    // 5.FIXED 设置缓存大小
    // val channel = Channel<Int>(1)


    val producer = GlobalScope.launch {
        for (i in 0 .. 3) {
            Logit.d("cfx sending $i")
            channel.send(i)
            Logit.d("cfx sent $i")
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        while (!channel.isClosedForReceive) {
            Logit.d("cfx receiving")
            val value = channel.receiveOrNull()
            Logit.d("cfx received $value")
        }
    }

    producer.join()
    consumer.join()
}

/**
 * channel 的迭代
 */
suspend fun iterateChannel() {
    val channel = Channel<Int>(Channel.UNLIMITED)

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            Logit.d("sending $i")
            channel.send(i)
            Logit.d("sent $i")
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        for (i in channel) {
            Logit.d("received $i")
        }
    }

    producer.join()
    consumer.join()
}

suspend fun producer() {
    val receiveChannel = GlobalScope.produce(capacity = Channel.UNLIMITED) {
        for (i in 0..3) {
            Logit.d("sending $i")
            send(i)
            Logit.d("sent $i")
        }
    }

    val consumer = GlobalScope.launch {
        for (i in receiveChannel) {
            Logit.d("received $i")
        }
    }

    consumer.join()
}

suspend fun consumer() {
    val sendChannel = GlobalScope.actor<Int>(capacity = Channel.UNLIMITED) {
        for (i in this) {
            Logit.d("received $i")
        }
    }

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            Logit.d("sending $i")
            sendChannel.send(i)
            Logit.d("sent $i")
        }
    }

    producer.join()
}

suspend fun broadcast() {
    // val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)

    val broadcastChannel = GlobalScope.broadcast {
        for (i in 0..5) {
            send(i)
        }
    }

    List(5) { index ->
        GlobalScope.launch {
            val receiveChannel = broadcastChannel.openSubscription()
            for (i in receiveChannel) {
                Logit.d("[#$index] received $i")
            }
        }
    }.joinAll()
}