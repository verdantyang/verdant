/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */
#include <stdio.h>

/**
 * Definition for singly-linked list.
 */
struct ListNode {
	int val;
	ListNode *next;
	ListNode(int x) : val(x), next(NULL) {}
};

/* Solution */
class Solution {
public:
	/** @param head The linked list's head.
	    Note that the head is guaranteed to be not null, so it contains at least one node. */
	Solution(ListNode* head) {

	}

	/** Returns a random node's value. */
	int getRandom() {

	}
};


/* Test Case */
int main() {
	ListNode head = new ListNode(1);
	head.next = new ListNode(2);
	head.next.next = new ListNode(3);
	Solution sol = new Solution(head);
	int param_1 = sol.getRandom();
	return 0;
}