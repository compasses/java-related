package util;

import java.util.List;

/**
 * Created by i311352 on 9/8/16.
 */
public class addTwoNumbers {
    /**
     * Definition for singly-linked list.
     * */
      public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }

      public static  String toString(ListNode list) {
          StringBuilder stringBuilder = new StringBuilder();
          while (list != null) {
              stringBuilder.append(list.val + "->");
              list = list.next;
          }
          return stringBuilder.toString();
      }



        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode newList = null, newListHead = null;
            int nextcarrier = -1;
            while (l1 != null || l2 != null) {

                int carrier = (l1 == null?0:l1.val)
                        + (l2 == null ? 0:l2.val) + (nextcarrier == -1 ? 0 : nextcarrier);

                int left;
                if (carrier >= 10) {
                    left = carrier % 10;
                    nextcarrier = 1;
                } else {
                    left = carrier;
                    nextcarrier = -1;
                }

                if (newList == null) {
                    newList = new ListNode(left);
                    newListHead = newList;
                } else {
                    newList.next = new ListNode(left);
                    newList = newList.next;
                }

                if (l1 != null)
                    l1 = l1.next;
                if (l2 != null)
                    l2 = l2.next;
            }

            if (nextcarrier != -1) {
                newList.next = new ListNode(nextcarrier);
            }

            return newListHead;
        }

        public static void tes1() {
            ListNode l1 = new ListNode(9);
            l1.next = new ListNode(9);
//            l1.next.next = new ListNode(9);
//            l1.next.next.next = new ListNode(9);
            System.out.println("l1 =" + toString(l1));
            ListNode l2 = new ListNode(1);
//            l2.next = new ListNode(6);
//            l2.next.next = new ListNode(4);
            System.out.println("l2 =" + toString(l2));

            System.out.println("Solution=" + toString(addTwoNumbers(l1, l2)));
        }
    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);
        System.out.println("l1 =" + toString(l1));
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println("l2 =" + toString(l2));

        System.out.println("Solution=" + toString(addTwoNumbers(l1, l2)));
        tes1();

    }

}
