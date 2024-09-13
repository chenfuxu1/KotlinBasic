package chapter06.singleton

object SingleTonField {
    private const val TAG = "SingleTonField"

    // 属性 x 不生成 getter / setter, 等同于 java field
    @JvmField var x: Int = 333

    @JvmStatic fun y() {
        println("$TAG y...")
    }
}