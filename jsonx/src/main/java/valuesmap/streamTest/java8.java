package valuesmap.streamTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by i311352 on 7/9/16.
 */
public class java8 {

    public static void main(String[] args) {

        Stream<String> words = Stream.of("Java", "Magazine", "is",
                "the", "best");

//        Map<String, Long> letterToCount =
//                words.map(w -> w.split(""))
//                        .flatMap(Arrays::stream)
//                        .collect(Collectors.toMap());
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("../jsonx","jsonx.iml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.lines(Paths.get("../jsonx", "jsonx.iml"))
                    .map(line -> line.split("\\s+")) // Stream<String[]>
                    .flatMap(Arrays::stream) // Stream<String>
                    .distinct() // Stream<String[]>
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


        new Thread(()->System.out.println("hi")).start();

        List<String> strs = Arrays.asList("de","C", "a", "B", "d");
        //strs.stream().flatMap(s -> s.toUpperCase());
        Collections.sort(strs, String::compareToIgnoreCase);
        System.out.print(strs);

        LocalDateTime coffeeBreak = LocalDateTime.now().plusHours(2);
        System.out.println(coffeeBreak);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hi");
            }
        };
        r1.run();
        int i = 0;
        Runnable r2 = ()-> {System.out.println("hi");};
        r2.run();
        //Function
        //Supplier
        List<Integer> numbers = Arrays.asList(1, 2,3,4,5,6,7,8);

        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println("Sum=" + sum);

        int factor = numbers.stream().reduce(1, (a,b)->a*b);
        System.out.println("factor=" + factor);

        BinaryOperator<Integer> bsum = (a, b) -> a+b;
        Integer res = bsum.apply(1,2); // yields 3
        Function<Integer,Integer> add1 = x -> x + 1;
        Function<Integer,Integer> mul3 = x -> x * 3;

        BinaryOperator<Function<Integer,Integer>> compose = (f,g) -> x -> g.apply(f.apply(x));
        Function<Integer,Integer> h = compose.apply(add1,mul3);
        Integer bres = h.apply(10); //yields 33


        Function<Integer,Integer> hh = mul3.compose(add1);
        Integer bbres = hh.apply(10);
    }
}
