package concurrency.produceconsum;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by i311352 on 06/04/2017.
 */
public class Main {
    public static void main(String args[]) {
        BlockingQueue<ConsumeData> queue = new LinkedBlockingDeque<>(10);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Producer producer4 = new Producer(queue);
        Producer producer5 = new Producer(queue);

        Consumer consumer = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);
        Consumer consumer4 = new Consumer(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(producer1);
        executorService.execute(producer2);
        executorService.execute(producer3);
        executorService.execute(producer4);
        executorService.execute(producer5);

        executorService.execute(consumer);
        executorService.execute(consumer2);
        executorService.execute(consumer3);
        executorService.execute(consumer4);
    }

}
