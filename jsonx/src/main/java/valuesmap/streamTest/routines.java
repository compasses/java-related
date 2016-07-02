package valuesmap.streamTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by i311352 on 7/1/2016.
 */
public class routines {
    public static void main(String[] args) {

        Integer[] sixNums = {1, 2, 3, 4, 5, 6, 6, 6};
        List<Integer> lists = Arrays.asList(sixNums);
        System.out.println(sixNums.toString());
        List<Integer> integerList = lists.stream().map(n->n*n).collect(Collectors.toList());
        System.out.println(integerList.toString());
        Set<Integer> sets = lists.stream().map(n->n).collect(Collectors.toSet());

        System.out.println(sets.toString());

        //Integer[] evens = Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);

    }
}
