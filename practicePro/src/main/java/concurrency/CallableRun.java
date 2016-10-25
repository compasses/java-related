package concurrency;

import java.util.concurrent.Callable;

/**
 * Created by I311352 on 6/24/2016.
 */
public class CallableRun implements Callable<String> {
    private int id;
    public CallableRun(int id) {
        this.id = id;
    }
    public String call() {
        return "result of Callable " + id;
    }
}
