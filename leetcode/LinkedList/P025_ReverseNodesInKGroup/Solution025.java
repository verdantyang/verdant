package LinkedList.P025_ReverseNodesInKGroup;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution025 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur = head;
        int count = 0;
        while (cur != null && count != k) {  // find the k+1 node
            cur = cur.next;
            count++;
        }

        if (count == k) {
            cur = reverseKGroup(cur, k); // reverse list with k+1 node as head

            while (count-- > 0) { // reverse current k-group:
                ListNode temp = head.next;
                head.next = cur;
                cur = head;
                head = temp;
            }
            head = cur;
        }
        return head;
    }
}
