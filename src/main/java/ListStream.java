import java.util.ArrayList;
import java.util.List;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 16:41
 **/

public class ListStream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        /**
         * forEach 是终止操作符，相当于自来水管的水龙头
         * 如果不调用 forEach，只调用其他函数，那么什么也不会输出
         * stream 是懒汉式，调用 forEach，前面的流操作 filter、map 才会真正执行
         * kotlin 中的等价写法是 list.asSequence
         */
        list.stream()
                .filter(it -> {
                    System.out.println("filter: " + it);
                    return it % 2 == 0;
                })
                .map(it -> {
                    System.out.println("map: " + it);
                    return it * 2;
                })
                .forEach(it -> {
                    System.out.println("result: " + it);
                });
    }
}
