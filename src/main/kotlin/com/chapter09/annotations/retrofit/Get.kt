package com.chapter09.annotations.retrofit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-28 23:36
 *
 * Desc:
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Get(val url: String = "") {
}