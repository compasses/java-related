package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by i311352 on 5/23/2017.
 */
public class HashMapTest {
    public static void main(String args[]) {
        Map<String, Integer> integerMap = new HashMap<>();

        integerMap.put("1", 1);
        integerMap.put("2", 2);

        List<Integer> t = Arrays.asList((Integer[]) integerMap.values().toArray());
    }
}
