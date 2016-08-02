package rxTesting;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by i311352 on 7/25/2016.
 */
public class firestTry {
    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }

        });
    }
}
