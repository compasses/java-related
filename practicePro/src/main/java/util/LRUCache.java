package util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.boot.test.IntegrationTest;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by i311352 on 22/02/2017.
 */
class LRUCache2 {

}

public class LRUCache{
    private int   capacity;
    private int   currentCount;
    private Node  cacheLinkHead;
    private Node  cacheLinkTail;

    private HashMap<Integer, Node> cache;

    class Node {
        int key;
        int val;
        Node next;
        Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.currentCount = 0;
        this.cacheLinkHead = null;
        this.cacheLinkTail = null;

        cache = new HashMap<Integer, Node>(100);
    }

    private boolean isFull() {
        return this.currentCount >= this.capacity;
    }

    private Node loopFind(int key) {
        Node find = cacheLinkHead;
        if (find == null) {
            return null;
        }

        while (find != null) {
            if (find.key == key) {
                return find;
            }
            find = find.next;
        }

        return null;
    }

    private void moveToHead(Node node) {
        Node find = cacheLinkHead;
        while (find != null) {
            if (find == node) {
                if (find != cacheLinkHead) {
                    int tempKey = cacheLinkHead.key;
                    int tempVal = cacheLinkHead.val;
                    cacheLinkHead.key = find.key;
                    cacheLinkHead.val = find.val;
                    find.key = tempKey;
                    find.val = tempVal;
                }
                break;
            }

            find = find.next;
        }
    }

    private void addNode(Node newNode) {
        currentCount++;
        if (cacheLinkHead == null) {
            // the first one
            cacheLinkHead = newNode;
            cacheLinkTail = newNode;

            return;
        }

        newNode.next = cacheLinkHead;
        cacheLinkHead.prev = newNode;
        cacheLinkHead = newNode;
    }

    public synchronized void set(int key,int value) {
        // first check exist or not
        Node find = loopFind(key);
        if (find != null) {
            // find it, move it to head
            find.val = value;
            moveToHead(find);
        } else {
            if (isFull()) {
                // swap the oldest one
                cacheLinkTail.key = key;
                cacheLinkTail.val = value;
                moveToHead(cacheLinkTail);
            } else {
                // add node to the head
                Node newNode = new Node(key, value);
                addNode(newNode);
            }
        }

        System.out.println("currentCount " + currentCount);
    }

    public synchronized int get(int key) throws ClassNotFoundException{
        Node find = loopFind(key);
        //


        if (find != null) {
            int found = find.val;
            moveToHead(find);
            return found;
        }
        // throw exception, will define it in somewhere
        throw new ClassNotFoundException("");
    }

public static void test() throws ClassNotFoundException{
    LRUCache cache = new LRUCache(2);
    cache.set(1, 1);
    cache.set(2, 2);
    cache.set(3, 2);
    //assertEquals cache.get(3) == 21;
    //assert cache.get(2) == 2;
}



//

//  ["LRUCache","put","put","get","put","get","put","get","get","get"]
//          [[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
//

//    ["LRUCache","put","put","get","put","put","get"]
//            [[2],[2,1],[2,2],[2],[1,1],[4,1],[2]]
//
//

public static void main(String[] args) throws ClassNotFoundException{
    //test();
    LRUCache cache = new LRUCache(2);
    cache.set(2, 1);
    cache.set(2,2);
    System.out.println("Get " + cache.get(2));
    cache.set(1, 1);
    cache.set(4, 1);

    System.out.println("Get " + cache.get(2));

//    ExecutorService service = Executors.newCachedThreadPool();
//    LRUCache cache = new LRUCache(100);
//
//    service.submit(() ->{
//        for (int i = 0; i < 100; i++) {
//            cache.set(i, i*10);
//        }
//    });
//
////    service.submit(() ->{
////        for (int i = 0; i < 200; i++) {
////            cache.set(i, i*10);
////        }
////    });
////    for (int i = 0; i < 200; i++) {
////        cache.set(i, i*10);
////    }
////    service.submit(() ->{
////        for (int i = 0; i < 200; i++) {
////            cache.set(i, i*10);
////        }
////    });
//
//    service.submit(() ->{
//        try {
//            for (int i = 0; i < 111; ++i) {
//                System.out.println("val " + cache.get(i));
//            }
//        } catch (ClassNotFoundException e){
//
//        }
//
//    });

}
}