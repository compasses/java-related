package concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by I311352 on 6/24/2016.
 */
public class EventChecker implements Runnable{
    private IntGenerator intGenerator;
    private final int id;
    public EventChecker(IntGenerator intGenerator, int id) {
        this.intGenerator = intGenerator;
        this.id = id;
    }
    public void run() {
        while (!intGenerator.isCaceled()) {
            int val = intGenerator.next();
            if (val%2 != 0) {
                System.out.println(val + " not even!");
                intGenerator.cacel();
            }
            //System.out.println(val + " is right!");
        }
    }

    public static void test(IntGenerator gp, int count) {
        System.out.println("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; ++i) {
            exec.execute(new EventChecker(gp, i));
        }
        exec.shutdown();
    }

    public static void test(IntGenerator gp) {
        test(gp, 10);
    }

}
