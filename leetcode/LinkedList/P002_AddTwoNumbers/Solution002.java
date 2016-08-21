package LinkedList.P002_AddTwoNumbers;

public class Solution002 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ret = new ListNode(0);
        ListNode cur = ret;
        boolean carry = false;
        while (l1 != null || l2 != null || carry) {
            cur.next = new ListNode(0);
            cur = cur.next;
            if (l1 == null && l2 != null)
                cur.val = l2.val;
            if (l2 == null && l1 != null)
                cur.val = l1.val;
            if (l1 != null && l2 != null)
                cur.val = l1.val + l2.val;
            if (carry) {
                cur.val += 1;
                carry = false;
            }
            if (cur.val >= 10) {
                cur.val -= 10;
                carry = true;
            }
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        return ret.next;
    }
}
