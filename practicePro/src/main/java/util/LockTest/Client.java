package util.LockTest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashSet;

/**
 * Created by I311352 on 1/18/2017.
 */
public class Client {

    public static void main(String[] args) {
        sizeOf(new Data(1));
//        HashSet set = new HashSet();
//        set.add(new URL("http://google.com"));
//        set.contains(new URL("http://google.com"));
//        Thread.sleep(60000);
//        set.contains(new URL("http://google.com"));
        Data data = new Data(10);

        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();

        new WriterThread(data, "ABCDEFGHI").start();
        new WriterThread(data, "012345789").start();
    }

    public static Unsafe getUnsafe() {
        Class cc = sun.reflect.Reflection.getCallerClass(2);
        if (cc.getClassLoader() != null)
        //    throw new SecurityException("Unsafe");
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long sizeOf(Object o) {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }
}
