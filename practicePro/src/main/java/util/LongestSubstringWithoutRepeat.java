package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.CheckedInputStream;

/**
 * Created by i311352 on 9/16/16.
 */
public class LongestSubstringWithoutRepeat {
//    Given a string, find the length of the longest substring without repeating characters.
//
//    Examples:
//
//    Given "abcabcbb", the answer is "abc", which the length is 3.
//
//    Given "bbbbb", the answer is "b", with the length of 1.
//
//    Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
//
//    Subscribe to see which companies asked this question

    public static void printArray(int[] a) {
        System.out.println("begin");
        Arrays.asList(a).stream().forEach(i
                -> System.out.print(" " + i + " "));
        System.out.println("end");
    }

    public static int lengthofsubstring3(String s) {
        HashMap<Character, Integer> maypkey = new HashMap<Character, Integer>();
        int maxLen = 0;
        int lastRepeatPos = -1;
        for (int i = 0; i < s.length(); ++i) {
            if (maypkey.get(s.charAt(i)) != null && lastRepeatPos < maypkey.get(s.charAt(i))) {
                lastRepeatPos = maypkey.get(s.charAt(i));
            }
            if (i - lastRepeatPos > maxLen) {
                maxLen = i - lastRepeatPos;
            }
            maypkey.put(s.charAt(i), i);
        }

        return maxLen;

    }

    public static int lenghtOfSubString2(String s) {
        int[] mapKey = new int[128];
        int longest = 0;
        int currentLongest = 0;
        for (int i = 0; i < s.length(); ++i) {
            currentLongest = 0;
            for (int j = i; j < s.length(); ++j) {
                mapKey[s.charAt(j)] ++;

                if (mapKey[s.charAt(j)] > 1) {
                    break;
                } else {
                    currentLongest++;
                }
            }
            for (int ii = 0; ii < 128; ++ii) {
                mapKey[ii] = 0;
            }

            if (currentLongest > longest)
                longest = currentLongest;
        }
        return longest;
    }

        public static int lengthOfLongestSubstring(String s) {
            if (s.length() == 0)
                return 0;
            if (s.length() == 1) {
                return  1;
            }

            int[] mapkey = new int[128];
            int[] resultArr = new int[s.length()];

            int round = 1;
            for (int i = 0; i < s.length(); ++i) {
                if (mapkey[s.charAt(i)] >= round) {
                    round ++;
                    resultArr[i] = 0;
                } else {
                    resultArr[i] = i;
                }
                mapkey[s.charAt(i)]++;
            }

            int longest = 0;
            int currentLongest = 0;
            for (int i = 0; i < s.length(); ++i) {
                if (resultArr[i] == 0 ) {
                    // new round begin
                    if (currentLongest > longest) {
                        longest = currentLongest;
                    }
                    currentLongest = 0;
                }
                currentLongest ++;
            }
            return  longest < currentLongest ? currentLongest : longest;
        }

        public static void main(String[] argc) {
            String ss = "abcabcbb";
            System.out.println("length " + lenghtOfSubString2(ss));

            String ss1 = "aab";
            System.out.println("length " + lenghtOfSubString2(ss1));

            String ss2 = "dvdf";
            System.out.println("length " + lenghtOfSubString2(ss2));


            String ss3 = "au";
            System.out.println("length " + lenghtOfSubString2(ss3));


            String ss4 = "ohomm";
            System.out.println("length " + lengthofsubstring3(ss4));
        }

}
