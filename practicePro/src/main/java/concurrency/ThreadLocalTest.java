package concurrency;

/**
 * Created by i311352 on 09/04/2017.
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
    private static Integer initConn = null;
//    static {
//        try {
//            initConn = DriverManager.getConnection("url, name and password");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public Integer getConn() {
        Integer c = tl.get();
        if(null == c) tl.set(initConn);
        return tl.get();
    }

    public static void  main(String args[]) {

    }

}
