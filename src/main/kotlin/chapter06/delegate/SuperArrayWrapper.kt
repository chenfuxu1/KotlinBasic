package chapter06.delegate

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 22:00
 *
 * 包装 List 和 Map，使集合既可以索引访问，又能 key 访问
 **/
class SuperArrayWrapper<E>(
    private val list: MutableList<E?> = ArrayList(),
    private val map: MutableMap<Any, E> = HashMap()
) : MutableList<E?> by list, MutableMap<Any, E> by map {

    override fun isEmpty(): Boolean {
        return list.isEmpty() && map.isEmpty()
    }

    override val size: Int
        get() = list.size + map.size

    override fun clear() {
        list.clear()
        map.clear()
    }

    override operator fun set(index: Int, element: E?): E? {
        if (list.size <= index) {
            repeat(index - list.size + 1) {
                list.add(null)
            }
        }
        return list.set(index, element)
    }

    override fun toString(): String {
        return "List: $list Map: $map"
    }
}

fun main() {
    val superArray = SuperArrayWrapper<String>()
    superArray += "Hello"
    println(superArray) // [Hello]{}

    superArray["key1"] = "荒天帝"
    superArray[1] = "云曦"
    superArray[2] = "火灵儿"
    superArray[10] = "哈哈"
    println(superArray) // [Hello, 云曦, 火灵儿, null, null, null, null, null, null, null, 哈哈]{key1=荒天帝}

}