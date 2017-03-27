package LinkedList.P237_DeleteNodeInALinkedList;

import LinkedList.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(1)
 * @Space Complexity:  O(1)
 */
public class Solution237 {

    public void deleteNode(ListNode node) {
        if (node.next == null) {
            node = null;
            return;
        } else
            node.val = node.next.val;
        node.next = node.next.next == null ? null : node.next.next;
    }
}
