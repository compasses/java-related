package concurrency;

/**
 * Created by i311352 on 7/30/16.
 */
import java.util.concurrent.*;
import static util.util.print;
import static util.util.printnb;

class Meal {
    private final int orderNum;
    public Meal(int orderNum) { this.orderNum = orderNum; }
    public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;
    public WaitPerson(Restaurant r) { restaurant = r; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while(restaurant.meal == null){
                        print("Waiting persion...");

                        wait(); // ... for the chef to produce a meal
                    }
                }
                print("Waitperson got " + restaurant.meal);
                synchronized(restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); // Ready for another
                }
            }
        } catch(InterruptedException e) {
            print("WaitPerson interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant r) { restaurant = r; }
    public boolean test() {
        print("Waiting Chef...");

        return restaurant.meal != null;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while(test()) {
                        wait(); // ... for the meal to be taken
                    }
                }
                if(++count == 10) {
                    print("Out of food, closing");
                    restaurant.exec.shutdownNow();
                }
                printnb("Order up! ");
                synchronized(restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch(InterruptedException e) {
            print("Chef interrupted");
        }
    }
}

public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);
    public Restaurant() {
        exec.execute(chef);

        exec.execute(waitPerson);
        exec.execute(waitPerson);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}