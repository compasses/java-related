package util.LockTest;

/**
 * Created by I311352 on 1/18/2017.
 */
public class ReadWriteLock {
    private int readThreadCounter = 0;
    private int waitingWriteCounter = 0;
    private int writeThreadCounter = 0;
    private boolean writeFlag = true; // write priorities


    // read lock
    public synchronized void readLock() throws InterruptedException {
        while (writeThreadCounter > 0 || (writeFlag && waitingWriteCounter > 0)) {
            wait();
            System.out.println("Waiting for read");
        }
        readThreadCounter ++;
    }

    public synchronized void readUnlock() {
        readThreadCounter --;
        writeFlag = true;
        notifyAll();
    }

    // write lock
    public synchronized void writeLock() throws InterruptedException {
        waitingWriteCounter ++;
        try {
            while (readThreadCounter > 0 || writeThreadCounter > 0) {
                wait();
                System.out.println("Waiting for write");
            }
        } finally {
            waitingWriteCounter --;
        }

        writeThreadCounter ++;
    }

    // write unlock
    public synchronized void writeUnlock() {
        writeThreadCounter --;
        // make read high priority
        writeFlag = false;
        notifyAll();
    }
}
