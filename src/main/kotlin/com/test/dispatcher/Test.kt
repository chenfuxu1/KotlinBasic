package com.test.dispatcher

import com.utils.Logit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-14 12:18
 *
 **/
fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        Logit.d("aaa")
        withContext(Dispatchers.IO) {
            Logit.d("bbb")
        }
        Logit.d("ccc")
    }

    Thread.sleep(111)
}