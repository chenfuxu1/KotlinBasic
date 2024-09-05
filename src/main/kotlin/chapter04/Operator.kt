package chapter04

/**
 * https://kotlinlang.org/docs/operator-overloading.html#operator-overloading.md
 */
fun main() {
    /**
     * public open operator fun equals(other: Any?): Boolean
     */
    "Hello" == "World"
    "Hello".equals("World")

    /**
     * public operator fun plus(other: Int): Int
     */
    2 + 3
    2.plus(3)

    /**
     * override fun contains(element: @UnsafeVariance E): Boolean
     */
    val list = listOf(1, 2, 3, 4)
    2 in list
    list.contains(2)

    val map = mutableMapOf(
        "Hello" to 2,
        "World" to 3
    )

    /**
     * public operator fun get(key: K): V?
     */
    val value = map["Hello"]
    val value2 = map.get("Hello")

    /**
     * public inline operator fun <K, V> MutableMap<K, V>.set(key: K, value: V): Unit
     */
    map["World"] = 4
    map.set("World", 5)

    /**
     *  public override operator fun compareTo(other: Int): Int
     */
    2 > 3
    2.compareTo(3)

    val func = fun() {
        println("Hello")
    }

    func.invoke()
    func() // 等价
}
