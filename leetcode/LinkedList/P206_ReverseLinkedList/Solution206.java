package LinkedList.P206_ReverseLinkedList;

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
