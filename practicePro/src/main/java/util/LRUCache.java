package util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.boot.test.IntegrationTest;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by i311352 on 22/02/2017.
 */
class LRUCache2 {
    private int capacity;
    private Hashtable<Integer, Node> cache;
    private Node cacheLinkHead;

    public LRUCache2(int capacity) {
        this.capacity  = capacity;
        this.cache = new Hashtable<Integer, Node>(capacity);
    }

    public void set(int key, int val) {
        Node find = cache.get(key);

        if (find != null) {
            if (find == cacheLinkHead) {
                cacheLinkHead.val = val;
            } else {
                appendToHead(find, key, val);
            }
            //if (find != cacheLinkHead) {
            //}
        } else {
            if (isFull()) {
                Node tailNode = cacheLinkHead.prev;
                appendToHead(tailNode, key, val);
            } else {
                Node node = new Node(key, val);
                appendToHead(node);
            }
        }
    }

    public int get(int key) {
        Node find = cache.get(key);
        if (find != null) {
            if (find != cacheLinkHead) {
                appendToHead(find, key, find.val);
            }
            return find.val;
        }

        return -1;
    }
    private void appendToHead(Node find, int key, int val) {
        find.prev.next = find.next;
        find.next.prev = find.prev;
        cache.remove(find.key);

        find.val = val;
        find.key = key;

        find.next = cacheLinkHead;
        find.prev = cacheLinkHead.prev;

        cacheLinkHead.prev.next = find;
        cacheLinkHead.prev = find;

        cacheLinkHead = find;

        cache.put(key, find);
    }


    private void appendToHead(Node node) {

        if (cacheLinkHead == null) {
            cacheLinkHead = node;
        } else {
            node.next = cacheLinkHead;
            node.prev = cacheLinkHead.prev;

            cacheLinkHead.prev.next = node;
            cacheLinkHead.prev = node;

            cacheLinkHead = node;
        }

        cache.put(node.key, node);
    }

    private boolean isFull() {
        return cache.size() == capacity;
    }

    public void printFromHead () {
        Node head = cacheLinkHead;
        System.out.println("head: " + head.key +":"+ head.val);
        while (head.next != cacheLinkHead) {
            head = head.next;
            System.out.println("->: " + head.key +":"+ head.val);
        }
    }




    private static class Node {
        int key;
        int val;

        Node next;
        Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.next = this.prev = this;
        }
    }

    public static void main(String[] args) {
        LRUCache2 cache2 = new LRUCache2(2);
        cache2.set(2, 1);
        cache2.set(3, 2);

        System.out.println("Get " + cache2.get(3));
        cache2.printFromHead();
        System.out.println("Get " + cache2.get(2));
        cache2.printFromHead();

        cache2.set(4, 3);
        cache2.printFromHead();

        System.out.println("Get " + cache2.get(2));
        System.out.println("Get " + cache2.get(3));

        System.out.println("Get " + cache2.get(4));


//        LRUCache2 cache = new LRUCache2(3);
//        cache.set(1, 1);
//        cache.set(2,2);
//        cache.set(3,3);
//        cache.printFromHead();
//        System.out.println("Get " + cache.get(2));
//        cache.printFromHead();
//
//        cache.set(4, 4);
//        cache.set(5, 5);
//        System.out.println("Get " + cache.get(1));
//        System.out.println("Get " + cache.get(3));
//        cache.printFromHead();

    }

}

public class LRUCache {

    private int   capacity;
    private int   currentCount;
    private Node  cacheLinkHead;

    class Node {
        int key;
        int val;
        Node next;
        Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.next = this.prev = this;
        }
    }

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.currentCount = 0;
    }

    private boolean isFull() {
        return this.currentCount >= this.capacity;
    }

    private Node loopFind(int key) {
        Node find = cacheLinkHead;
        if (find == null) {
            return null;
        }

        do {
            if (find.key == key) {
                return find;
            }
            find = find.next;
        } while(find != cacheLinkHead);

        return null;
    }

    private void moveToHead(Node node) {
        if (node == cacheLinkHead) {
            return ;
        }
        node.next.prev = node.prev;
        node.prev.next = node.next;
        appendToHead(node);
    }

    private void appendToHead(Node newNode) {
        newNode.next = cacheLinkHead;
        newNode.prev = cacheLinkHead.prev;

        cacheLinkHead.prev.next = newNode;
        cacheLinkHead.prev = newNode;
        cacheLinkHead = newNode;
    }

    private void addNode(Node newNode) {
        currentCount++;
        if (cacheLinkHead == null) {
            // the first one
            cacheLinkHead = newNode;
            return;
        }

        appendToHead(newNode);

    }

    public void put(int key,int value) {
        // first check exist or not
        Node find = loopFind(key);
        if (find != null) {
            // find it, move it to head
            find.val = value;
            moveToHead(find);
        } else {
            if (isFull()) {
                // swap the oldest one
                Node tailNode = cacheLinkHead.prev;
                tailNode.key = key;
                tailNode.val = value;
                moveToHead(tailNode);
            } else {
                // add node to the head
                Node newNode = new Node(key, value);
                addNode(newNode);
            }
        }

    }

    public int get(int key) {
        Node find = loopFind(key);

        if (find != null) {
            int found = find.val;
            moveToHead(find);
            return found;
        }
        // throw exception, will define it in somewhere
        return -1;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */