package concurrency.produceconsum;

import java.time.DateTimeException;
import java.util.Date;

/**
 * Created by i311352 on 06/04/2017.
 */
public class StaticSingleton {
    private final Date time;

    private StaticSingleton(Date time) {
        this.time = time;
        System.out.println("Static Singleton is created");
    }

    private static class SingletonHolder {
        private static StaticSingleton Instance = new StaticSingleton(new Date());
    }

    public Date getDate() {
        return this.time;
    }

//    public void setDate(Date date) {
//        this.time = date;
//    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.Instance;
    }


    public static void main(String args[]) {

        StaticSingleton staticSingleton = StaticSingleton.getInstance();

        System.out.println("Instance time is " + staticSingleton.getDate());

        StaticSingleton staticSingleton2 = StaticSingleton.getInstance();

        System.out.println("Instance time is " + staticSingleton2.getDate());
    }

}
