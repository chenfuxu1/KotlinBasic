package com.chapter06.example.jsonresult.bean

import kotlinx.serialization.Serializable

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:21
 *
 **/
@Serializable
data class Person(val name: String, val age: Int)
