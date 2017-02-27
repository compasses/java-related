package util;

import concurrency.IntGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by i311352 on 25/02/2017.
 */

public class HashTest {
    private Node<Integer, Integer>[] table;
    private int size;
    private int count = 16;

    public HashTest() {
        this.table = (Node<Integer, Integer>[]) new Node[count];
    }

    public void put(int key, int value) {
        int hashCode = hash(key);
        int ind = indexFor(hashCode, count);
        System.out.println("Index is " + ind);

        Node n = table[ind];
        if (n == null) {
            n = new Node(ind, key, value, null);
            table[ind] = n;
        } else {
            Node newNode = new Node(ind, key, value, n);
            table[ind] = newNode;
        }

        size++;
    }

    public int get(int key) {
        int hashCode = hash(key);
        int ind = indexFor(hashCode, count);

        Node n = table[ind];

        if (n == null) {
            return -1;
        }
        while (n != null) {
            if ((int)n.key == key) {
                return (int) n.value;
            }
            n = n.next;
        }
        return -1;
    }

    public int indexFor(int h, int length) {
        return h & (length - 1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            Node n = table[i];
            stringBuilder.append("table " + i + " -> ");
            while (n != null) {
                stringBuilder.append(n.key + " : " + n.value + " ");
                n = n.next;
            }
            stringBuilder.append("\r\n");
        }

        return "HashTest{" +
                "table=" + Arrays.toString(table) + stringBuilder.toString() +
                ", size=" + size +
                ", count=" + count +
                '}';
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }


    static class StringManipulate {
        private int[] data = new int[255];

        public void getUniqueChar(String str) {
            // System.arraycopy();
            for (int i = 0; i < str.length(); ++i) {
                data[(int)str.charAt(i)] ++;
            }
            for (int i = 0; i < str.length(); ++i) {
                char a = str.charAt(i);

                if (data[(int)str.charAt(i)] == 1) {
                    System.out.println(" got it " + str.charAt(i));
                    break;
                }
            }
        }

        public static void main(String[] args) {
            StringManipulate manipulate = new StringManipulate();
            manipulate.getUniqueChar("abcdabcdefg");
        }
    }

    public static void main(String[] args) {
        HashTest test = new HashTest();
        test.put(111, 2);
        test.put(222, 2);
        test.put(2, 2);
        test.put(3, 2);
        test.put(4, 2);
        for (int i = 0; i < 100; ++i) {
            test.put(i, i*10);
        }

        System.out.println("Get " + test.get(111));
        System.out.println("Get " + test.get(222));

        System.out.println("tale " + test.toString());
    }
}
