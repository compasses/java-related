package jbp.stringsutil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by i311352 on 22/11/2016.
 */
public class stringsearch {
    public static List<Integer> findAll(String pattern, String source) {
        List<Integer> idx = new ArrayList<>();
        int id = -1;
        int shift = pattern.length();
        int scIndex = -shift;
        while (scIndex != -1 || id == -1) {
            idx.add(scIndex);
            id = scIndex + shift;
            scIndex = source.indexOf(pattern, id);
        }
        idx.remove(0);
        return idx;
    }

    public static List<Integer> findAllByMatch(String pattern, String source) {
        List<Integer> idx = new ArrayList<>() ;
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(source);
        while (matcher.find())
            idx.add(matcher.start());
        return idx;
    }
}
