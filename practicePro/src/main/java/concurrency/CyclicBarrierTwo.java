package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by i311352 on 2/24/2017.
 */

class Solver{
    int N;
    float[][] matrix;
    CyclicBarrier barrier;
    boolean finish;

    class Worker implements Runnable{
        int myRow;
        public Worker(int row) {myRow = row;}

        private boolean done() {
            return finish;
        }

        private void processRow(int myRow) {
            float[] data = matrix[myRow];
            for (int i = 0; i < data.length; ++i) {
                data[i] *=2;
            }
        }

        @Override
        public void run() {

        while (!done()) {
                processRow(myRow);
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                return;
            } catch (BrokenBarrierException ex) {
                return;
            }
            }
        }
    }

    public void mergeRows() {
        float sum = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                sum += matrix[i][j];
            }
        }

        System.out.println("all finished " + sum);
        finish = true;
    }

    public Solver(float[][] data) throws InterruptedException{
        matrix = data;
        N = matrix.length;

        Runnable barrierAction = () -> {mergeRows();};

        barrier = new CyclicBarrier(N, barrierAction);

        List<Thread> threads = new ArrayList<Thread>(N);
        for (int i = 0; i < N; i++) {
            Thread thread = new Thread(new Worker(i));
            threads.add(thread);
            thread.start();
        }

      // wait until done
//      for (Thread thread : threads)
//        thread.join();
    }

    public static void main(String[] args) throws InterruptedException {
        float[][] matrix = {{1,1,1},{1,1,1}};

        Solver solver = new Solver(matrix);

    }
}
public class CyclicBarrierTwo {

}
