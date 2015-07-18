/**
 * @Time Complexity: O(1)
 */
#include <stdio.h>

/**
 * Definition for singly-linked list.
 */
struct ListNode {
	int val;
	struct ListNode *next;
};

/* Solution */
void deleteNode(struct ListNode* node) {
	if (NULL != node->next)
		node->val = node->next->val ;
	node->next = node->next->next ? node->next->next : NULL;
}

/* Test Case */
int main() {
}