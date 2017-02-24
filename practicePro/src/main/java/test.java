/**
 * Created by i311352 on 22/02/2017.
 */
public class test {
    private static int DEFAULT_LENGTH = 8;
    public static String asc2bin(String str) {
        StringBuilder binaryString = new StringBuilder(8 * str.length());
        for (int i = 0; i < str.length(); i++) {
            try {
                int ascInt = (int) str.charAt(i);
                StringBuilder temp = new StringBuilder(Integer.toBinaryString(ascInt));
                System.out.println("temp string:" + temp + " int is " + ascInt);
                String bin = null;
                if (temp.length() <= DEFAULT_LENGTH) {
                    while (temp.length() < DEFAULT_LENGTH) {
                        temp.insert(0, "0");
                    }
                    bin = temp.toString();
                } else if (temp.length() > DEFAULT_LENGTH) {
                    continue;
                }
                binaryString.append(bin);
            } catch (NumberFormatException nfe) {
                continue;
            }
        }
        return binaryString.toString();
    }
    public static String bin2asc(String str) {
        StringBuilder sb = new StringBuilder();
        //10010001100101 split into 8 characters
        for (int i = 0; i < str.length() - 1; i += DEFAULT_LENGTH) {
            //grab the hex in pairs
            String output = str.substring(i, (i + DEFAULT_LENGTH));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 2);
            //convert the decimal to character
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    public class Solution {
        public int HammingDistance(int a, int b) {
            String aBin = Integer.toBinaryString(a);
            String bBin = Integer.toBinaryString(b);

            int aLen = aBin.length();
            int bLen = bBin.length();

            int maxLen = Math.max(aLen, bLen);
            StringBuilder aSb = new StringBuilder(aBin);
            StringBuilder bSb = new StringBuilder(bBin);


            while (aLen < maxLen) {
                aSb.insert(0, '0');
                aLen++;
            }
            while (bLen < maxLen) {
                bSb.insert(0, '0');
                bLen++;
            }
            int sum = 0;
            String aF = aSb.toString();
            String bF = bSb.toString();

            for (int i = 0; i < maxLen; i++) {
                if (aF.charAt(i) != bF.charAt(i)) {
                    sum++;
                }
            }
            return sum;
        }
        public int totalHammingDistance(int[] nums) {

            int sum  = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j ++) {
                    sum += HammingDistance(nums[i], nums[j]);
                }
            }
            return sum;
        }

    }
    public static void main(String[] args) {
        String binary = asc2bin("Hello world!");
        System.out.println("binary : " + binary);
        String asc = bin2asc(binary);
        System.out.println("ASC : " + asc);



        int[] testArr = {4, 14, 2};
        //Solution solution = new test.Solution();
        //System.out.println("totoal " + new test.Solution().totalHammingDistance(testArr));
    }
}

