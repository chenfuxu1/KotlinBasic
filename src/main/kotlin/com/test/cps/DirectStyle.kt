package com.test.cps

import com.utils.Logit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-07 15:31
 *
 * Desc: 直接风格 Direct Style
 */
private fun loadDataAndUpdate() {
    val dataA = loadDataA() // step 1
    val dataB = loadDataB(dataA) // step 2
    update(dataB) // step 3
}

private fun loadDataA(): String {
    return "Direct"
}

private fun loadDataB(content: String): String {
    return "$content Style"
}

private fun update(content: String) {
    Logit.d("update: $content")
}

fun main() {
    loadDataAndUpdate()
}