package chapter03

/**
 * 扩展方法
 */
class PoorGuy {
    var pocket: Double = 0.0
}

// 扩展方法
fun PoorGuy.noMoney() {
    println("i no money")
}

// 扩展属性 = backing field + getter + setter
var PoorGuy.moneyLeft: Double
    get() {
        return this.pocket
    }
    set(value) {
        pocket = value
    }

interface Guy {
    /**
     * 这里获取不到 field，因为接口只能描述行为，不能描述状态
     * 那为什么接口可以定义属性 var moneyLeft: Double，而 java 中不行呢
     * 因为 java 中只是一个 field，而 kotlin 中是 backing field + getter + setter
     */
    var moneyLeft: Double
        get() {
            return 0.0
        }
        set(value) {}

    fun noMoney() {
        println("no money called")
    }
}