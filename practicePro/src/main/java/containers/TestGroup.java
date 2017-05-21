package containers;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by i311352 on 5/18/2017.
 */

public class TestGroup {
    public static void main(String[] args) {

        List<TestStruct> structs = new ArrayList<>();
        structs.add(new TestStruct(1L, 1L, 10.0));
        structs.add(new TestStruct(1L, 1L, 0.0));

        structs.add(new TestStruct(2L, 2L, 0.0));
        structs.add(new TestStruct(2L, 2L, 0.0));

        Map<String, List<TestStruct>> r2 = structs.stream().collect(
                Collectors.groupingBy(TestStruct::getId)
        );

        TestStruct str1 = structs.stream().filter(s->s.getPrice()>0).findFirst().get();




        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        Map<String, Long> finalMap = new LinkedHashMap<>();

        //Sort a map and add to finalMap
        result.entrySet().stream()
              .sorted(Map.Entry.<String, Long>comparingByValue()
                      .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

        System.out.println(finalMap);
    }
}
