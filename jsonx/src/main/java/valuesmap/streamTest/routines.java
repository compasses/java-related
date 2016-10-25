package valuesmap.streamTest;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by i311352 on 7/1/2016.
 */
public class routines {
    public static void main(String[] args) {
        List<String> myList = Arrays.asList("a1", "a2", "a3","bb");
        List<Integer> myList2 = Arrays.asList(1,2,4,5);
        Stream<String> words = Stream.of(myList.toString());
        Map<String, Long> letterToCount =
                words.map(w -> w.split(""))
                        .flatMap(Arrays::stream)
                        .collect(groupingBy(identity(), counting()));

        System.out.println(letterToCount);
        //Map<String, Integer> testMap = Stream.of(myList, myList2).collect(Collectors.toMap(myList::get, myList2::get));



        myList.stream().filter(s->s.startsWith("b")).map(String::toUpperCase).sorted().forEach(System.out::println);

        Arrays.asList("a1", "a2", "a3").stream().findFirst().ifPresent(System.out::println);
        Stream.of(myList).findFirst().ifPresent(System.out::println);

        Stream.of("d1", "d2", "d3", "d5").filter(n -> {
            System.out.println("Filter: " + n);
            return true;
        }).forEach(s -> System.out.println("Foreach: " + s));

        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .allMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return true;//s.startsWith("A");
                });

        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));


        Integer[] sixNums = {1, 2, 3, 4, 5, 6, 6, 6};
        List<Integer> lists = Arrays.asList(sixNums);
        System.out.println(sixNums.toString());
        List<Integer> integerList = lists.stream().map(n->n*n).collect(Collectors.toList());
        System.out.println(integerList.toString());
        Set<Integer> sets = lists.stream().map(n->n).collect(Collectors.toSet());

        System.out.println(sets.toString());

        //Integer[] evens = Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);

        class Person {
            String name;
            int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());

        System.out.println(filtered);    // [Peter, Pamela]

        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(groupingBy(p -> p.age));

        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

        // age 18: [Max]
        // age 23: [Peter, Pamela]
        // age 12: [David]

        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge);     // 19.0

        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);

        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);
        // In Germany Max and Peter and Pamela are of legal age.

        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));

        System.out.println(map);
        // {18=Max, 23=Peter;Pamela, 12=David}


        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);  // MAX | PETER | PAMELA | DAVID

        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok


    }
}
