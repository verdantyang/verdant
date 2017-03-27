package LinkedList.P206_ReverseLinkedList;

import LinkedList.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution206 {

    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode pnow = head;
        ListNode pnext = head.next;
        head.next = null;
        while (pnext != null) {
            ListNode temp = pnext.next;
            pnext.next = pnow;
            pnow = pnext;
            pnext = temp;
        }
        return pnow;
    }
}
