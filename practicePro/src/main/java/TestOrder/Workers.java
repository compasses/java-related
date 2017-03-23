package TestOrder;

/**
 * Created by i311352 on 03/03/2017.
 */
public class Workers extends Thread {
    private WorkerQueue queue = new WorkerQueue(10);

    @Override
    public void run() {
        addTask(() -> {
            System.out.println("Yes, i am running... ");
        });
        Runnable runnable = (Runnable) queue.getReq();
        runnable.run();
    }

    public void addTask(Runnable r) {
        queue.putReq(r);
    }

    public static void main(String args[]) throws InterruptedException {

        Workers workers = new Workers();

        workers.addTask(() -> {
            System.out.println("Yes, i am running.22.. ");
        });
        workers.start();
        workers.join();
    }
}
