package LinkedList.P234_PalindromeLinkedList;

import struct.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution234 {

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        boolean res = true;
        ListNode one;
        ListNode two = head;
        ListNode next = head.next;
        ListNode cursorLeft = head;
        ListNode cursorRight = head;
        while (two != null && two.next != null) {
            one = next;
            two = two.next.next;

            //even
            if (two == null) {
                cursorRight = one;
            }
            //odd
            else if (two.next == null) {
                cursorRight = one.next;
            } else {
                next = one.next;
                one.next = cursorLeft;
                cursorLeft = one;
            }
        }
        while (cursorRight != null) {
            res &= cursorLeft.val == cursorRight.val;
            cursorLeft = cursorLeft.next;
            cursorRight = cursorRight.next;
        }
        return res;
    }
}
