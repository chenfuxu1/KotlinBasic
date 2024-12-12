package com.test.cps

import com.utils.Logit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-07 15:48
 *
 * Desc: 续体传接风格
 */
private fun loadDataAndUpdate() {
    loadDataA { dataA -> // step 1
        loadDataB(dataA) { dataB -> // step 2
            update(dataB) // step 3
        }
    }
}

private fun loadDataA(dataA: (String) -> Unit) {
    dataA("CPS")
}

private fun loadDataB(content: String, dataB: (String) -> Unit) {
    dataB("$content Style")
}

private fun update(content: String) {
    Logit.d("update: $content")
}

fun main() {
    loadDataAndUpdate()
}