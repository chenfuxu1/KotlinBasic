package chapter05

import java.io.File

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 16:22
 *
 * 统计字符个数
 *
 * public inline fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
 * }
 **/
fun main() {
    val groupBy = File("build.gradle").readText().toCharArray()
        .filterNot {
            // 过滤空字符
            it.isWhitespace()
        }
        .groupBy {
            /**
             * 根据返回值作为 key
             * value 我 List<T>
             * 返回 map 集合
             */
            it
        }
    println("groupBy: $groupBy")

    /**
     * 将 Map<Char, List<Char>> 转换为 List<Pair<Char, Int>>
     */
    val listPair = File("build.gradle").readText().toCharArray()
        .filterNot {
            // 过滤空字符
            it.isWhitespace()
        }
        .groupBy {
            /**
             * 根据返回值作为 key
             * value 我 List<T>
             * 返回 map 集合
             */
            it
        }
        .map {
            it.key to it.value.size
        }
        .let {
            println("result: $it" )
        }
}
