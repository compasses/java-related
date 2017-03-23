package concurrency;

/**
 * Created by i311352 on 9/5/16.
 */
import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID:"
                    + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
//        ExecutorService es = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            es.submit(task);
//            //es.execute(task);
//        }
//        es.shutdown();
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            executor.submit(task);
        }

    }
}

