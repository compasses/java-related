package util;

/**
 * Created by I311352 on 1/20/2017.
 */
public class ContendTest extends Thread {
    private static class DataHolder {
        private volatile long l1 = 0;
        private volatile long l2 = 0;
        private volatile long l3 = 0;
        private volatile long l4 = 0;
    }

    private static DataHolder dataHolder = new DataHolder();
    private static long nLoops;

    public ContendTest(Runnable t) {
        super(t);
    }

    public static void main(String[] args) throws Exception {
        nLoops = 1000000;
        ContendTest[] contendTests = new ContendTest[8];

        contendTests[0] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l1 += i;
            }
        });

        contendTests[1] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l2 += i;
            }
        });

        contendTests[2] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l3 += i;
            }
        });

        contendTests[3] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l4 += i;
            }
        });
        contendTests[4] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l1 += i;
            }
        });

        contendTests[5] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l2 += i;
            }
        });

        contendTests[6] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l3 += i;
            }
        });

        contendTests[7] = new ContendTest(() -> {
            for (long i = 0; i < nLoops; i ++) {
                dataHolder.l4 += i;
            }
        });

        long then = System.currentTimeMillis();
        for (ContendTest ct : contendTests) {
            ct.start();
        }
        for (ContendTest ct : contendTests) {
            ct.join();
        }
        long now = System.currentTimeMillis();
        System.out.println("Duration: " + (now - then) + "ms");
    }

}
