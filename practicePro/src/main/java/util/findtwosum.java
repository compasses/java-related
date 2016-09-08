package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by i311352 on 9/7/16.
 */
public class findtwosum {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
//    public class Solution {
//        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//
//        }
//    }


    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> indexMap = new HashMap();

        for (int i = 0; i < nums.length; ++i) {
            Integer key = target-nums[i];

            if (indexMap.get(nums[i]) != null) {
                result[0] = indexMap.get(nums[i]);
                result[1] = i;
                return  result;
            } else {
                indexMap.put(key, i);
            }
        }
        return result;
    }

    public static void main(String args[]) {
        int arra[] = {2, 3, 4};

        int result[] = twoSum(arra, 6);
        System.out.println(twoSum(arra, 6));
    }
}
