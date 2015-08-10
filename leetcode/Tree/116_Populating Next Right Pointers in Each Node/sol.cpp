/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */
#include <stdio.h>

/**
 * Definition for binary tree with next pointer.
 */
struct TreeLinkNode {
	int val;
	struct TreeLinkNode *left, *right, *next;
};

/* Solution */
void connect(struct TreeLinkNode *root) {
	if (!root)
		return;
	struct TreeLinkNode *curlev;
	while (root->left) {
		curlev = root;
		while (curlev) {
			curlev->left->next = curlev->right;
			if (curlev-> next)
				curlev->right->next = curlev->next->left;
			curlev = curlev->next;
		}
		root = root->left;
	}
}
