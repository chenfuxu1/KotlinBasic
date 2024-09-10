package chapter06.delegate

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-10 23:16
 *
 * Desc:
 */
class StateManager {
    /**
     * public inline fun <T> observable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Unit):
     *     ReadWriteProperty<Any?, T> =
     *     object : ObservableProperty<T>(initialValue) {
     *          override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(property, oldValue, newValue)
     * }
     *
     * ObservableProperty: 实现了 getValue / setValue 方法
     * ObservableProperty 的实例代理了属性 state 的 getter / setter
     */
    var state: Int by Delegates.observable(0) { property, oldValue, newValue ->
        // 每次 set 时，就会调用 lambda 表达式
        println("State changed from $oldValue -> $newValue")
    }
}

class Foo {
    val x: Int by X(::x)

    var y: Int by X(::y)
}


class X(val property: KProperty<*>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return 2
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, i: Int) {
        println("delegate i $i")
    }
}

fun main() {
    val stateManager = StateManager()
    stateManager.state = 3
    stateManager.state = 4

    println(Foo().x)
    Foo().y = 5
}
