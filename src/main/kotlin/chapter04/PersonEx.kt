package chapter04

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 11:09
 *
 **/
class PersonEx(var age: Int, var name: String) {
    override fun equals(other: Any?): Boolean {
        val other =  (other as? PersonEx) ?: return false
        return other.age == age && other.name == name
    }

    override fun hashCode(): Int {
        return 1 + 7 * age + 13 * name.hashCode()
    }
}

fun main() {
    val personSet = HashSet<PersonEx>()
    // (0..5).forEach {
    //     personSet += PersonEx(20, "Huang")
    // }
    val person = PersonEx(20, "慌")
    personSet += person
    person.age++
    /**
     * 发现移除不了
     * 因为 key 对象发生变化了，找不到 person 的原因 hashCode 了
     * 如果需要修改属性，建议重新创建一个对象
     * val person2 = PersonEx(person.age++, person.name)
     * 容易内存泄漏
     */
    personSet.remove(person)
    println(personSet.size)
}