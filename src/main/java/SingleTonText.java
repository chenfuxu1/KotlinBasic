import com.chapter06.singleton.SingleTonField;
import com.chapter06.singleton.SingleTonStatic;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-11 23:53
 * <p>
 * Desc:
 */
public class SingleTonText {
    public static void main(String[] args) {
        // java 中直接调用 kotlin 的静态成员
        System.out.println( SingleTonStatic.getX());
        SingleTonStatic.setX(200);
        SingleTonStatic.y();

        // 属性 x 不生成 getter / setter, 等同于 java field
        System.out.println(SingleTonField.x);
    }
}
