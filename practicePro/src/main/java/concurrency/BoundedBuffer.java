package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by i311352 on 8/19/16.
 */
@ThreadSafe
public class BoundedBuffer <V> extends BaseBoundedBuffer<V> {
    // CONDITION PREDICATE: not-full (!isFull())
    // CONDITION PREDICATE: not-empty (!isEmpty())
    public BoundedBuffer() {
        this(100);
    }

    public BoundedBuffer(int size) {
        super(size);
    }

    // BLOCKS-UNTIL: not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull())
            wait();
        doPut(v);
        notifyAll();
    }

    public synchronized void Put2(V v) throws InterruptedException {
        while (isFull())
            wait();
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty)
            notifyAll();
    }

    // BLOCKS-UNTIL: not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();
        return v;
    }

    // BLOCKS-UNTIL: not-full
    // Alternate form of put() using conditional notification
    public synchronized void alternatePut(V v) throws InterruptedException {
        while (isFull())
            wait();
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty)
            notifyAll();
    }
    public static  void main(String args[]) {
        ExecutorService exec = Executors.newCachedThreadPool();
        BoundedBuffer<Long> boundedBuffer = new BoundedBuffer<>();

        Random random = new Random();
        IntStream.range(0, 80).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        boundedBuffer.put(random.nextLong());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            });
                }
        );

        IntStream.range(0, 8).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        boundedBuffer.take();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                }
        );
    }
}

