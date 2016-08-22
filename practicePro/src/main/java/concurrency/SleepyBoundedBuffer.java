package concurrency;

/**
 * Created by i311352 on 8/19/16.
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * SleepyBoundedBuffer
 * <p/>
 * Bounded buffer using crude blocking
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SleepyBoundedBuffer <V> extends BaseBoundedBuffer<V> {
    int SLEEP_GRANULARITY = 60;

    public SleepyBoundedBuffer() {
        this(100);
    }

    public SleepyBoundedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty())
                    return doTake();
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
    public static  void main(String args[]) {
        ExecutorService exec = Executors.newCachedThreadPool();
        SleepyBoundedBuffer<Long> sleepyBoundedBuffer = new SleepyBoundedBuffer<>();

        Random random = new Random();
        IntStream.range(0, 80).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        sleepyBoundedBuffer.put(random.nextLong());
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
                                        sleepyBoundedBuffer.take();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                }
        );
    }
}
