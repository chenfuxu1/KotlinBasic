package com.chapter06.example.jsonresult.ks

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 0:05
 *
 * Desc:
 */
@Serializable
data class PersonWithDate(
    val name: String, val age: Int,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date
)

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    // override val descriptor: SerialDescriptor = StringDescriptor.withName("Date")

    override fun serialize(encoder: Encoder, obj: Date) {
        encoder.encodeString(df.format(obj))
    }

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }
}