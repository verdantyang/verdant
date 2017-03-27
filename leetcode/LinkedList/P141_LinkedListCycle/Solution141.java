package LinkedList.P141_LinkedListCycle;

import LinkedList.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution141 {

    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;

        ListNode oneStep = head;
        ListNode twoStep = head;
        while (twoStep != null && twoStep.next != null) {
            oneStep = oneStep.next;
            twoStep = twoStep.next.next;
            if (twoStep != null && oneStep.val == twoStep.val)
                return true;
        }
        return false;
    }
}
