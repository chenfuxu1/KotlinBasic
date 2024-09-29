package com.chapter09.annotations.retrofit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-28 23:36
 *
 * Desc:
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Api(val url: String) {
}