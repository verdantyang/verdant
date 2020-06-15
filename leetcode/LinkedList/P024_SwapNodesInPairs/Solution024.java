package LinkedList.P024_SwapNodesInPairs;

import struct.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointers
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution024 {

    public ListNode swapPairs(ListNode head) {
        ListNode start = new ListNode(0);
        start.next = head;

        ListNode prev = start;
        ListNode first = start.next;
        while (first != null && first.next != null) {
            ListNode second = first.next;
//            ListNode temp = second.next;
            prev.next = second;
            first.next = second.next;   //temp store
            second.next = first;

            prev = first;
            first = prev.next;
        }
        return start.next;
    }
}
