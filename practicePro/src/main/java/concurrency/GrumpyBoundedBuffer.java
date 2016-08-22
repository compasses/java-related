package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by i311352 on 8/19/16.
 */
@ThreadSafe
public class GrumpyBoundedBuffer <V> extends BaseBoundedBuffer<V> {
    public GrumpyBoundedBuffer() {
        this(100);
    }

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull())
            throw new BufferFullException();
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty())
            throw new BufferEmptyException();
        return doTake();
    }

    public static  void main(String args[]) {
        ExecutorService exec = Executors.newCachedThreadPool();
        GrumpyBoundedBuffer<Long> grumpyBoundedBuffer = new GrumpyBoundedBuffer<>();

        Random random = new Random();
        IntStream.range(0, 8).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                while (!Thread.interrupted())
                                    grumpyBoundedBuffer.put(random.nextLong());
                            });
                }
        );

        IntStream.range(0, 8).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                while (!Thread.interrupted())
                                    grumpyBoundedBuffer.take();
                            });
                }
        );
    }
}

class ExampleUsage {
    private GrumpyBoundedBuffer<String> buffer;
    int SLEEP_GRANULARITY = 50;

    void useBuffer() throws InterruptedException {
        while (true) {
            try {
                String item = buffer.take();
                // use item
                break;
            } catch (BufferEmptyException e) {
                Thread.sleep(SLEEP_GRANULARITY);
            }
        }
    }
}

class BufferFullException extends RuntimeException {
}

class BufferEmptyException extends RuntimeException {
}
