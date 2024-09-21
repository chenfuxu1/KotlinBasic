package com.chapter06.example.jsonresult.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 0:05
 *
 * Desc:
 */
data class PersonWithDate(val name: String, val age: Int, val birthDate: Date)

object DateSerializer {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    @ToJson
    fun serialize(date: Date) = df.format(date)

    @FromJson
    fun deserialize(date: String) = df.parse(date)
}