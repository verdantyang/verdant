package LinkedList.P024_SwapNodesInPairs;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointers
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution024 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode swapPairs(ListNode head) {
        ListNode start = new ListNode(0);
        start.next = head;
        ListNode first = start;
        ListNode second = start.next;
        while (second != null && second.next != null) {
            ListNode third = second.next;
            first.next = third;
            second.next = third.next;
            third.next = second;

            first = second;
            second = first.next;
        }
        return start.next;
    }
}
