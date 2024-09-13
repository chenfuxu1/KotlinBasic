package chapter06.enumclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-13 23:51
 *
 * Desc: 枚举定义扩展
 */
enum class State5 {
    One, Two, Three, Four, Five
}

fun State5.next(): State5 {
    return State5.values().let {
        val nextOrdinal = (ordinal + 1) % it.size
        it[nextOrdinal]
    }
}

fun main() {
    println(State5.One.next())
    println(State5.Five.next())
}