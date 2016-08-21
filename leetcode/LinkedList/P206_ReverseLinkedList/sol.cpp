/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
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

class Solution {
public:
	ListNode* reverseList(ListNode* head) {
		if (NULL == head)
			return head;
		ListNode* pcurrent;
		ListNode* pnext;
		ListNode* ptemp;

		pcurrent = head;
		pnext = pcurrent -> next;
		pcurrent -> next = NULL;
		
		while (NULL != pnext) {
			ptemp = pnext -> next;
			pnext -> next = pcurrent;
			pcurrent = pnext;
			pnext = ptemp;
		}
		return pcurrent;
	}
};