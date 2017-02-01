package util.LockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by I311352 on 1/18/2017.
 */
public class Data {
    private final char[] buffer;
    private ReadWriteLock readWriteLock = new ReadWriteLock();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();


    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; ++i) {
            this.buffer[i] = '*';
        }
    }

    public String read() throws InterruptedException{
        readWriteLock.readLock();
        try {
            return doRead();
        } finally {
            readWriteLock.readUnlock();
        }
    }

    public void write(char c) throws InterruptedException {
        readWriteLock.writeLock();
        try {
            doWrite(c);
        } finally {
            readWriteLock.writeUnlock();
        }
    }

    public String doRead() {
        StringBuilder result = new StringBuilder();
        for (char c: buffer) {
            result.append(c);
        }
        sleep(100);
        return result.toString();
    }

    public  void doWrite(char c) {
        for (int i = 0; i < this.buffer.length; ++i) {
            buffer[i] = c;
            sleep(100);
        }
    }

    private void sleep(long ms) {
        try {
            TimeUnit.MICROSECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
