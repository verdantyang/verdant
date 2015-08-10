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
	bool hasCycle(ListNode *head) {
		ListNode* oneStep = head;
		ListNode* twoStep = head;
		while (twoStep && twoStep->next) {
			twoStep = twoStep->next->next;
			oneStep = oneStep->next;
			if (oneStep == twoStep)
				return true;
		}
		return false;
	}
};
