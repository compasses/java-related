package concurrency;

/**
 * Created by i311352 on 9/1/16.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore1 implements Runnable{
    final Semaphore semp = new Semaphore(20);
    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            semp.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final Semaphore1 demo=new Semaphore1();
        for(int i=0;i<20;i++){
            exec.submit(demo);
        }
        System.out.println("Waiting out....");
    }
}
