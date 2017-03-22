package threadpooltest;

/**
 * Created by i311352 on 17/03/2017.
 */
public class TestTask extends Task {
    private int i;

    public TestTask(int i){
        this.i = i;
    }

    public void run(){
        System.out.println("Task " + i + " is RUNNING.");
    }
}
