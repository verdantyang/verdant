public class Solution {
	/**
	 * Definition for singly-linked list.
	 */
	public class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}

	public void deleteNode(ListNode node) {
		if (null != node.next)
			node.val = node.next.val;
		node.next = node.next.next != null ? node.next.next : null;
	}
}