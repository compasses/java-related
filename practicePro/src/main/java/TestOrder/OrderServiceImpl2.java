package TestOrder;

import java.util.concurrent.*;

/**
 * Created by i311352 on 03/03/2017.
 */

public class OrderServiceImpl2 {
    private ArrayBlockingQueue<Runnable> blockingDeque = new ArrayBlockingQueue<Runnable>(10);
    private ExecutorService executorService = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.MILLISECONDS, blockingDeque);

    public void placeOrder(Object param) throws InterruptedException, ExecutionException {
        Future future = executorService.submit(()->{
            // do place order
            return param;
        });

        System.out.println("Get result " + future.get());
    }


    public static void main(String[] args)  throws InterruptedException, ExecutionException{
        OrderServiceImpl2 impl2 = new OrderServiceImpl2();

        ExecutorService executors = Executors.newCachedThreadPool();
        executors.execute(() -> {
            try {
                for (int i = 0; i < 100; ++i) {
                    impl2.placeOrder(Integer.valueOf(i));
                }
            } catch (InterruptedException e) {

            } catch (ExecutionException e) {

            }
        });

        impl2.placeOrder(Integer.valueOf(99));
    }
}
