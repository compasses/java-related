package concurrency.futuretest;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by I311352 on 8/4/2016.
 */
public class howfirest {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException, ExecutionException  {
        FactorialCalculator task = new FactorialCalculator(10);
        System.out.println("Submitting Task ...");

        Future future = threadPool.submit(task);
        System.out.println("Task is submitted");

        while (!future.isDone()) {
            System.out.println("Task is not complete yet ...");
            Thread.sleep(1000);
        }

        System.out.println("Task is completed, let's check result");
        Long factorial =  (Long) future.get();
        System.out.println("Factorial of 1000000 is : " + factorial);
        threadPool.shutdown();
    }

    private static class FactorialCalculator implements Callable <Long> {
        private int number;
        public FactorialCalculator(int number) {
            this.number = number;
        }


        public Long call() {
            long output = 0;
            try {
                output = factorial(number);
            } catch (InterruptedException ex) {
                Logger.getLogger(howfirest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return output;
        }

        private long factorial(int number) throws InterruptedException {
            if (number < 0) {
                throw  new IllegalArgumentException("Number must be greater than zero");
            }
            long result = 1;
            while (number > 0) {
                Thread.sleep(1000);
                result = result * number;
                number--;
            }
            return result;
        }
    }
}
