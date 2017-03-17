package util;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by i311352 on 11/03/2017.
 */
public class SingleClass {
    private static volatile SingleClass instance;
    private  Date timestamp;

    private SingleClass() {}

    public static SingleClass getInstance() {
        if (instance == null) {
            synchronized (SingleClass.class) {
                if (instance == null) {
                    instance = new SingleClass();
                    instance.setTimeStamp();
                }
            }
        }
        return instance;
    }

    private void setTimeStamp() {
        this.timestamp = new Date();
    }

    public Date getTimeStamp() {
        return timestamp;
    }

    public static void main(String[] args) {

        SingleClass singleClass = SingleClass.getInstance();

        System.out.println("Time is " + singleClass.getTimeStamp());
    }

}
