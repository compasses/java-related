package nio;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by i311352 on 7/14/16.
 */
public class MemoryInput {
    public static void main(String[] args)
            throws IOException {
        StringReader in = new StringReader(
                BufferedInputFile.read("pom.xml"));
        int c;
        while((c = in.read()) != -1)
            System.out.print((char)c);
    }
}