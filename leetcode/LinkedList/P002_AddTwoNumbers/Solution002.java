package LinkedList.P002_AddTwoNumbers;

import struct.ListNode;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(max(m,n))
 * @Space Complexity:  O(max(m,n))
 */
public class Solution002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //ret相当于锚点，用于方便找到结果
        ListNode ret = new ListNode(0);
        ListNode cur = ret;
        boolean carry = false;
        while (l1 != null || l2 != null || carry) {
            cur.next = new ListNode(0);
            cur = cur.next;
            if (l1 == null && l2 != null)
                cur.val = l2.val;
            if (l1 != null && l2 == null)
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
