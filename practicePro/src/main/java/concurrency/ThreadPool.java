package concurrency;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.*;

/**
 * Created by I311352 on 6/24/2016.
 */
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++)
            exec.execute(new LiftOff());
        exec.shutdown();

        ExecutorService execFix = Executors.newFixedThreadPool(10);
        ArrayList<Future<String>> futures = new ArrayList<Future<String>>();

        for (int j = 0; j < 10; j++) {
            futures.add(execFix.submit(new CallableRun(j)));
        }
        try {
            Thread.sleep(10L);
        } catch (Exception e) {
            System.out.println("Sleep failed ..");
        }
        for (Future<String> fs : futures) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                System.out.println(e);
                break;
            } catch (ExecutionException e) {
                System.out.println(e);
            }finally {
                execFix.shutdown();
            }
        }
    }


}
