package com.chapter06.example.jsonresult.gson

import com.google.gson.*
import java.lang.reflect.Type
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

object Serializers {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    object DateDeserializer : JsonDeserializer<Date> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
            return df.parse(json.asString)
        }
    }

    object DateSerializer : JsonSerializer<Date> {
        override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(df.format(src))
        }
    }
}