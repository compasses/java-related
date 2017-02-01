package util.LockTest;

/**
 * Created by I311352 on 1/18/2017.
 */
public class WriterThread extends Thread {
    private final Data data;
    private final String str;
    private int index = 0;

    public WriterThread(Data data, String str) {
        this.data = data;
        this.str = str;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = next();
                data.write(c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private char next() {
        char c = str.charAt(index);
        index ++;

        if (index >= str.length()) {
            index = 0;
        }

        return c;
    }
}
