package LinkedList.P206_ReverseLinkedList;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution206 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode pnext = head.next;
        ListNode pnow = head;
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
