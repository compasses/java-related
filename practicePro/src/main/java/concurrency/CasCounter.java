package concurrency;

/**
 * Created by i311352 on 8/23/16.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CasCounter
 * <p/>
 * Nonblocking counter using CAS
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class CasCounter {
    private MyCAS value = new MyCAS();


    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }

    public static void main(String args[]) throws InterruptedException {
        AtomicInteger a;
        CasCounter casCounter = new CasCounter();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            for (int i = 0; i < 100; ++i) {
                casCounter.increment();
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 100; ++i) {
                casCounter.increment();
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 100; ++i) {
                casCounter.increment();
            }
        });


        TimeUnit.SECONDS.sleep(5);
        System.out.println("cas is " + casCounter.getValue());

    }
}

class MyCAS {
    private int  val;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public int get() {
        return val;
    }

    public void set(int val) {
        this.val = val;
    }

    public int compareAndSwap(int expectedValue, int newValue) {
        reentrantLock.lock();
        int oldValue = get();
        if (oldValue  == expectedValue) {
            this.val = newValue;
        }
        reentrantLock.unlock();
        return oldValue;
    }

    public boolean compareAndSet(int expectedValue, int newValue) {
        reentrantLock.lock();
        try {
            return (expectedValue == compareAndSwap(expectedValue, newValue));
        } finally {
            reentrantLock.unlock();
        }
    }
}

@ThreadSafe
class SimulatedCAS {
    @GuardedBy("this") private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue,
                                           int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue)
            value = newValue;
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue,
                                              int newValue) {
        return (expectedValue
                == compareAndSwap(expectedValue, newValue));
    }
}
