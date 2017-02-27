package util;

import concurrency.ThreadPool;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by i311352 on 26/02/2017.
 */

class DivTask implements Runnable {
    int a, b;
    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public void run() {
        double re = a/b;
        System.out.println(a/b);
    }
}

public class ThreadTest {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
    static SynchronousQueue<Runnable> runnables =  new SynchronousQueue<Runnable>();

    static TracedThreadPool executor2 =
            new TracedThreadPool(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, runnables);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            executor.submit(new DivTask(100, i));
        }


        for (int i = 0; i < 5; i++) {
            executor2.submit(new DivTask(100, i));
        }
    }
}
