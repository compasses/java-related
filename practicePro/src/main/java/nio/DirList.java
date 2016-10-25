package nio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by i311352 on 7/13/16.
 */
public class DirList {
    public static FilenameFilter filter(final String regex) {
        // Creation of anonymous inner class:
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        }; // End of anonymous inner class
    }

    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            list = path.list(filter(args[0]));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String dirItem : list)
            System.out.println(dirItem);
    }
}
//public class DirList {
//    public static void main(String[] args) {
//        File path = new File(".");
//        String[] list;
//        if(args.length == 0)
//            list = path.list();
//        else
//            list = path.list(new DirFilter(".java"));
//        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
//        for(String dirItem : list)
//            System.out.println(dirItem);
//    }
//}
//
//class DirFilter implements FilenameFilter {
//    private Pattern pattern;
//    public DirFilter(String regex) {
//        System.out.println("reading" + regex);
//        pattern = Pattern.compile(regex);
//    }
//    public boolean accept(File dir, String name) {
//        return pattern.matcher(name).matches();
//    }
//}