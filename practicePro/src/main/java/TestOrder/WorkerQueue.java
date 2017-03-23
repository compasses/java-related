package TestOrder;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by i311352 on 03/03/2017.
 */

public class WorkerQueue {
    private int capacity;
    private LinkedList<Object> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition fullCondition = lock.newCondition();
    private Condition nullCondition = lock.newCondition();

    public WorkerQueue(int capacity) {
        this.capacity = capacity;
    }

    public void putReq(Object o) {
        lock.lock();
        try {
            while (this.queue.size() >= this.capacity) {
                fullCondition.await();
            }
            this.queue.add(o);
            nullCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object getReq() {
        Object o = null;
        lock.lock();
        try {
            while (this.queue.size() == 0) {
                nullCondition.await();
            }
            o  = this.queue.poll();
            fullCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return o;
    }

}
