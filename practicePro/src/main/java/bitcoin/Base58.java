package bitcoin;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by i311352 on 5/20/2017.
 */
public class Base58 {
    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO = ALPHABET[0];

    public Base58() {
    }

    public String encode(String str) {
        byte[] b = str.getBytes();
        String s_hex = DatatypeConverter.printHexBinary(b);
        long decimal = Long.valueOf(s_hex, 16);
        StringBuffer res  = new StringBuffer();
        System.out.println("decimal is :" + decimal);
        while (decimal > 0) {
            Long ind = decimal%58;
            char c = ALPHABET[ind.intValue()];
            res.append(c);
            decimal = decimal/58;
        }

        byte[] temp_b = str.getBytes();
        for (int i = 0; i < temp_b.length; ++i) {
            if (temp_b[i] != 0) {
                break;
            }
            res.append(ENCODED_ZERO);
        }
        return res.reverse().toString();
    }

    public String decode(String str) {
        long decimal = 0;
        char []chars = str.toCharArray();
        for(int i = 0; i < chars.length; ++i) {
            char tem_c = chars[i];
            int index_num = 0;
            for (int j = 0; j < ALPHABET.length; ++j) {
                if (ALPHABET[j] == tem_c) {
                    index_num = j;
                    break;
                }
            }
            decimal = decimal*58;
            decimal += index_num;
        }

        String s_hex = Long.toHexString(decimal);

        byte[] bytes = DatatypeConverter.parseHexBinary(s_hex);
        return new String(bytes);
    }

    public static void main(String [] args) {
        Base58 base = new Base58();
        System.out.println(base.encode("12345678"));
        System.out.println(base.decode("9EGJCxbxRGT"));
    }
}
