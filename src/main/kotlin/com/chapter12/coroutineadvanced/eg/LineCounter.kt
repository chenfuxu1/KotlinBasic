package com.chapter12.coroutineadvanced.eg

import com.utils.Logit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executors
import kotlin.jvm.internal.Intrinsics.Kotlin

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-31 23:41
 *
 * Desc: 字符统计
 */
data class FileLines(val file: File, val lines: Int) {
    override fun toString(): String {
        return "${file.name}: $lines"
    }
}

// kotlin 文件过滤规则
val KotlinFileFilter = { file: File ->
    file.isDirectory || file.name.endsWith(".kt")
}

suspend fun main() {
    val result = lineCounter(File("."))
    Logit.d(result)
}

suspend fun lineCounter(root: File): HashMap<File, Int> {
    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1).asCoroutineDispatcher()
        .use {
            withContext(it) {
                // 遍历文件，使用 1 个 channel
                val fileChannel = walkFile(root)
                // 读取行数，使用 5 个 channel
                var fileLineChannel = List(5) {
                    fileLineCounter(fileChannel)
                }
                resultAggregator(fileLineChannel)
            }
        }
}

fun CoroutineScope.walkFile(root: File): ReceiveChannel<File> {
    return produce(capacity = Channel.BUFFERED) {
        fileWalker(root)
    }
}

suspend fun SendChannel<File>.fileWalker(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.filter(KotlinFileFilter)?.forEach {
            fileWalker(it)
        }
    } else {
        send(file)
    }
}

// 计算一个文件的行数发送出去
fun CoroutineScope.fileLineCounter(input: ReceiveChannel<File>): ReceiveChannel<FileLines> {
    return produce(capacity = Channel.BUFFERED) {
        for (file in input) {
            file.useLines {
                send(FileLines(file, it.count()))
            }
        }
    }
}

suspend fun CoroutineScope.resultAggregator(channels: List<ReceiveChannel<FileLines>>): HashMap<File, Int> {
    val map = HashMap<File, Int>()
    // 将 channels 的结果聚合起来
    channels.aggregate { filteredChannels ->
        select<FileLines?> {
            filteredChannels.forEach {
                it.onReceive {
                    Logit.d("received FileLines: $it")
                    it
                }
            }
        }?.let {
            map[it.file] = it.lines
        }
    }
    return map
}

tailrec suspend fun List<ReceiveChannel<FileLines>>.aggregate(block: suspend (List<ReceiveChannel<FileLines>>) -> Unit) {
    block(this)
    filter {
        // 过滤已经接收完的 channel
        !it.isClosedForReceive
    }.takeIf { it.isNotEmpty() }?.aggregate(block)
}