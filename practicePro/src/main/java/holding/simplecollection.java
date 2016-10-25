package holding;

import java.util.*;

/**
 * Created by i311352 on 7/1/16.
 */

public class simplecollection {

    static Collection fill(Collection<String> collection) {
        collection.add("rat");
        collection.add("cat");
        collection.add("dog");
        collection.add("dog");
        return collection;
    }
    static Map fill(Map<String,String> map) {
        map.put("rat", "Fuzzy");
        map.put("cat", "Rags");
        map.put("dog", "Bosco");
        map.put("dog", "Spot");
        return map;
    }

    public static void main(String[] args) {
        util.util.print(fill(new ArrayList<String>()));
        util.util.print(fill(new LinkedList<String>()));
        util.util.print(fill(new HashSet<String>()));
        util.util.print(fill(new TreeSet<String>()));
        util.util.print(fill(new LinkedHashSet<String>()));
        util.util.print(fill(new HashMap<String,String>()));
        util.util.print(fill(new TreeMap<String,String>()));
        util.util.print(fill(new LinkedHashMap<String,String>()));

        Collection<Integer> c = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++)
            c.add(i); // Autoboxing
        for(Integer i : c)
            System.out.print(i + ", ");


        Collection<Integer> collection =
                new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Integer[] moreInts = { 6, 7, 8, 9, 10 };
        collection.addAll(Arrays.asList(moreInts));
        // Runs significantly faster, but you can't
        // construct a Collection this way:
        Collections.addAll(collection, 11, 12, 13, 14, 15);
        Collections.addAll(collection, moreInts);
        // Produces a list "backed by" an array:
        List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
        list.set(1, 99); // OK -- modify an element
    }


}
