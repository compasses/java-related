package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by i311352 on 9/18/16.
 */
public class RandomAccess  {

    public static void testRandomAccessFile() throws FileNotFoundException, IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("Data.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);

        int byteRead = fileChannel.read(byteBuffer);

        while (byteRead != -1) {
            System.out.println(" Read " + byteRead);

            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.print(" - " + (char) byteBuffer.get());
            }
            byteBuffer.clear();
            byteRead = fileChannel.read(byteBuffer);
        }
        randomAccessFile.close();
    }

    public static void main (String[] argc) throws IOException {
        testRandomAccessFile();
    }
}
