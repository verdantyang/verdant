package LinkedList.P237_DeleteNodeInALinkedList;

public class Solution237 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public void deleteNode(ListNode node) {
        if (node.next == null) {
            node = null;
            return;
        }
        if (node.next != null)
            node.val = node.next.val;
        node.next = node.next.next == null ? null : node.next.next;
    }
}
