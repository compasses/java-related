package TestOrder;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by i311352 on 03/03/2017.
 */

public class WorkerQueue {
    private int capacity;
    private int queueSize;
    private LinkedList<Object> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition fullCondition = lock.newCondition();
    private Condition nullCondition = lock.newCondition();

    public WorkerQueue(int capacity) {
        this.capacity = capacity;
        this.queueSize = 0;
    }

    public void putReq(Object o) {
        try {
            while (this.queueSize >= this.capacity) {
                fullCondition.await();
            }
            this.queue.add(o);
            nullCondition.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Object getReq() {
        Object o = null;
        try {
            while (this.queueSize == 0) {
                nullCondition.await();
            }
            o  = this.queue.poll();
            fullCondition.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return o;
    }

}
