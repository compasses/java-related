package concurrency;

import org.apache.tomcat.jni.Time;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by i311352 on 9/9/16.
 */
public class CompleteFutureDemo implements Runnable{
    CompletableFuture<Integer> integerCompletableFuture = null;

    public CompleteFutureDemo(CompletableFuture<Integer> re) {
        this.integerCompletableFuture = re;
    }
    public void run() {
        int re = 0;

        try {
            re = integerCompletableFuture.get() * integerCompletableFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Compute finish = " + re);

    }

    public static Integer calc(Integer para) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para*para;
    }

    public static void main(String args[]) throws InterruptedException , ExecutionException{
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new CompleteFutureDemo(future)).start();

        TimeUnit.SECONDS.sleep(1);

        future.complete(1000);

        Integer result;
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(()->calc(50)).thenApply(i->++i).thenApply(i-> {
            System.out.println(i);
            return i;
        });

        System.out.println("end future --> " + f1.get());

        // compose
        //CompletableFuture

    }
}
