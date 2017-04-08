package concurrency.produceconsum;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by i311352 on 06/04/2017.
 */
public class Producer implements Runnable {
    BlockingQueue<ConsumeData> queue;
    Boolean isRunning = true;
    private static AtomicInteger count = new AtomicInteger();
    private static final int SLEEPTIME = 1000;

    public Producer(BlockingQueue<ConsumeData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        ConsumeData data = null;
        Random r = new Random();

        System.out.println("start producer id=" + Thread.currentThread().getId());

        try {
            while (isRunning) {
                TimeUnit.MICROSECONDS.sleep(r.nextInt(SLEEPTIME));
                data = new ConsumeData(count.incrementAndGet());
                System.out.println(data + " is put into queue");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.err.println("failed to put data " + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }

}
