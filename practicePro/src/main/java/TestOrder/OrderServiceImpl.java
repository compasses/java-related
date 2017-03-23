package TestOrder;

import org.apache.catalina.*;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by i311352 on 03/03/2017.
 */
public class OrderServiceImpl implements OrderService, Callable<Object> {
    //private ExecutorService executorService = Executors.newSingleThreadExecutor();
//    WorkerThread thread = new WorkerThread();

static class WorkderThread extends Thread {
    private static WorkerQueue queue = new WorkerQueue(10);

    @Override
    public void run() {
        Object o = queue.getReq();
        System.out.println("Do place order....");
    }
}
    @Override
    public Object call() throws Exception {
    return null;
    }


    @Override
    public void placeOrder(Object param) {
    }

    public Object doPlaceOrder() {
    return null;
    }

//    public Object doPlaceOrder() {
//        try {
//            Future t = executorService.submit(initCallable());
//            return t.get();
//        } catch (InterruptedException e) {
//        } catch (ExecutionException e) {
//
//        }
//        return null;
//    }

//    public Callable initCallable() {
//        return () -> {
//            Object o = queue.getReq();
//            // start do place order
//            return o;
//        };
//    }

    public OrderServiceImpl() {
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        Integer integer1 = 1;
        orderService.placeOrder(integer1);

        ExecutorService executors = Executors.newSingleThreadExecutor();
        Future task = executors.submit(orderService);
        System.out.println("place return " + task.get());
    }

    static class WorkerQueue {
        private int capacity;
        private int queueSize;
        private LinkedList<Object> queue = new LinkedList<>();
        private ReentrantLock lock;
        private Condition fullCondition;
        private Condition nullCondition;

        public WorkerQueue(int capacity) {
            this.capacity = capacity;
            this.queueSize = 0;
            lock = new ReentrantLock();
            fullCondition = lock.newCondition();
            nullCondition = lock.newCondition();
        }

        public void putReq(Object o) {
            try {
                lock.lock();
                while (this.queueSize >= this.capacity) {
                    fullCondition.await();
                }
                this.queue.add(o);
                this.queueSize++;
                nullCondition.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public Object getReq() {
            Object o = null;
            try {
                lock.lock();
                while (this.queueSize == 0) {
                    nullCondition.await();
                }
                o  = this.queue.poll();
                this.queueSize--;
                fullCondition.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return o;
        }
    }

    //    private ArrayBlockingQueue<Runnable> blockingDeque = new ArrayBlockingQueue<Runnable>(10);
//
//    private ExecutorService executorService = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.MILLISECONDS, blockingDeque);
//
//    @Override
//    public void placeOrder(Object param) {
//
//    }
//
//
//
//    public Runnable placeOrderTask(Object param) {
//        return ()->{
//            // start place order
//
//        };
//    }
//
//
//    class placeOrderTask implements Runnable {
//        @Override
//        public void run() {
//
//        }
//    }
}
