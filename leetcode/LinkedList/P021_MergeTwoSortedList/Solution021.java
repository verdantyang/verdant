package LinkedList.P021_MergeTwoSortedList;

import struct.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(m+n)
 * @Space Complexity:  O(1)
 */
public class Solution021 {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null)
            return null;
        ListNode start = new ListNode(0);
        ListNode now = start;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                now.next = l1;
                l1 = l1.next;
            } else {
                now.next = l2;
                l2 = l2.next;
            }
            now = now.next;
        }
        if (l1 == null) {
            now.next = l2;
        }
        if (l1 != null) {
            now.next = l1;
        }
        return start.next;
    }
}
