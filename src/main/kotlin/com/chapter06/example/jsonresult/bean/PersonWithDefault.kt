package com.chapter06.example.jsonresult.bean

import kotlinx.serialization.Serializable

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-16 11:21
 *
 * 带默认值的实体类
 **/
@Serializable
data class PersonWithDefault(val name: String, val age: Int = 200)
