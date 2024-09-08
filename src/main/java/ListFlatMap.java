import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-07 22:53
 **/
public class ListFlatMap {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));

        list.stream().flatMap(e -> {
            ArrayList<Integer> integers = new ArrayList<>(e);
            for (int i = 0; i < e; i++) {
                integers.add(i);
            }
            return integers.stream();
        }).forEach(it -> {
            System.out.println("result：" + it);
        });

    }
}
