package nio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by i311352 on 7/14/16.
 */
public class BufferedInputFile {
    // Throw exceptions to console:
    public static String
    read(String filename) throws IOException {
        // Reading input by lines:
        BufferedReader in = new BufferedReader(
                new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while((s = in.readLine())!= null)
            sb.append(s + "\n");
        in.close();
        return sb.toString();
    }
    public static void main(String[] args)
            throws IOException {
        System.out.print(read("pom.xml"));
    }
}
