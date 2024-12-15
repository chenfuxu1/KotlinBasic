package com.test.coroutinecontext.coroutinename

import com.utils.Logit
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-14 12:25
 *
 **/
fun main() {
    val scope = CoroutineScope(CoroutineName("张三"))
    Logit.d(scope)

    Logit.d(A)
    Logit.d(A.K)
}

class A(val name: String) {
    companion object K : B("hah")

    override fun toString(): String {
        return super.toString()
    }
}

open class B(val name: String) {
    override fun toString(): String {
        return name
    }
}