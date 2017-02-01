package util.LockTest;

import nio.nio.channels.SystemInPipe;

/**
 * Created by I311352 on 1/18/2017.
 */
public class ReaderThread extends Thread {
    private final Data data;
    public ReaderThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long begin = System.currentTimeMillis();
                for (int i = 0; i < 10; ++i) {
                    String result = data.read();
                    System.out.println(Thread.currentThread().getName() + " => " + result);
                }
                long time = System.currentTimeMillis() - begin;
                System.out.println(Thread.currentThread().getName() + " -- " + time + "ms");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
