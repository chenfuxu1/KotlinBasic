package com.test.cps

import com.utils.Logit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-07 16:18
 *
 * Desc: kotlin 的黑魔法，Direct Style 实现 CPS 效果
 */
private suspend fun loadDataAndUpdate() {
    val dataA = loadDataA() // step 1, 需要传接续体 continuation
    val dataB = loadDataB(dataA) // step 2, 需要传接续体 continuation
    update(dataB) // step 3
}

private suspend fun loadDataA(): String {
    return "CPS suspend"
}

private suspend fun loadDataB(content: String): String {
    return "$content Style"
}

private suspend fun update(content: String) {
    Logit.d("update: $content")
}

suspend fun main() {
    loadDataAndUpdate()
}