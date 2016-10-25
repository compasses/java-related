package enumtype;

/**
 * Created by i311352 on 6/27/16.
 */
enum Activity{ SITIING, LYING, STANDING,HOPPING,RUNNING,DODGING,JUMPING,FALLING,FLYING}
public class RandomTest {
    public static void main(String[] args){
        for (int i = 0; i < 20; i++) {
            util.util.print(Enums.random(Activity.class) + " ");
        }
    }
}
