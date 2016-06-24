package concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by I311352 on 6/23/2016.
 */
public class LiftOff implements Runnable{
    protected int  countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;
    public LiftOff(){}
    public LiftOff(int countDown){
        this.countDown = countDown;
    }

    public String status(){
        return "#" +id+"("+(countDown>0?countDown:"LiftOff!")+")";
    }
    public  void run() {
        try {
            while (countDown-- >0) {
                System.out.println(status());
                //Thread.yield();
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("interrupted");
        }

    }
}
