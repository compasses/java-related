package concurrency;

/**
 * Created by I311352 on 6/24/2016.
 */
public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public synchronized int next() {
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args) {
        EventChecker.test(new EvenGenerator());
    }
}
