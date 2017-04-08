package concurrency.produceconsum;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by i311352 on 06/04/2017.
 */
public class Consumer implements Runnable {
    Boolean isRunning = false;
    BlockingQueue<ConsumeData> queue;
    AtomicInteger atomicInteger = new AtomicInteger();
    private static final int SLEEPTIME = 100;

    public Consumer(BlockingQueue<ConsumeData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start consume id = " + Thread.currentThread().getId());

        try {
            while (true) {
                ConsumeData consumeData = queue.take();
                if (null != consumeData) {
                    int re = consumeData.getInteger() * consumeData.getInteger();
                    System.out.println("result is " + re);
                    TimeUnit.MICROSECONDS.sleep(SLEEPTIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
