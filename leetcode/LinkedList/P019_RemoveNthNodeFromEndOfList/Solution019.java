package LinkedList.P019_RemoveNthNodeFromEndOfList;

import LinkedList.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointers
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution019 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 1; i <= n + 1; i++) {
            if (fast != null)
                fast = fast.next;
            else {
                if (i == n + 1)
                    return slow.next;
                else
                    return null;
            }
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return head;
    }
}
