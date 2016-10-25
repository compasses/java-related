package concurrency;

/**
 * Created by I311352 on 6/23/2016.
 */
public class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();

        ThreadRun();
    }
    private static void ThreadRun() {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for LiftOff!" + t.toString());
    }
}
