package jbp.stringsutil;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by i311352 on 21/11/2016.
 */
public class stringandbytes {
    public static byte[] stringToByteASCII(String str) {
        char[] buffer = str.toCharArray();
        byte[] bytes = new byte[buffer.length];
        for (int i = 0; i < buffer.length; ++i) {
            bytes[i] = (byte) buffer[i];
        }
        return bytes;
    }

    public static byte[] stringToBytesUTFCustom(String str) {
        char[] buffer = str.toCharArray();
        byte[] bytes = new byte[buffer.length << 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            bytes[bpos] = (byte) ((buffer[i]&0xFF00) >> 8);
            bytes[bpos+1] = (byte)(buffer[i]&0x00FF);
        }

        return bytes;
    }

    public static String bytesToStringUTFCustom(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for (int i = 0; i < bytes.length; ++i) {
            int bpos = i << 1;
            char c = (char) (((bytes[bpos]) & 0x00FF) >> 8 + (bytes[bpos + 1] & 0x00FF));
            buffer[bpos] = c;
        }
        return new String(buffer);
    }

    public static byte[] stringToBytesUTFNIO(String str) {
        char[] buffer = str.toCharArray();
        byte[] bytes = new byte[buffer.length << 1];
        CharBuffer charBuffer = ByteBuffer.wrap(bytes).asCharBuffer();
        for (int i = 0; i < buffer.length; ++i) {
            charBuffer.put(buffer[i]);
        }
        return bytes;
    }

    public static String bytesToStringUTFNIO(byte[] bytes) {
        CharBuffer charBuffer = ByteBuffer.wrap(bytes).asCharBuffer();
        return charBuffer.toString();
    }

    public static void main(String args[]) {
        byte[] b = stringToBytesUTFCustom("哥哥");
        assert stringToBytesUTFNIO("你好") == stringToBytesUTFCustom("你好");

      System.out.print("length :" + new String("哥哥").getBytes().length);
    }
}
