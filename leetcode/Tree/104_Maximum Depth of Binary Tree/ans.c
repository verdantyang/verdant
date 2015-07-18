/**
 * @Time Complexity: O(n)
 */
#include <stdio.h>

/**
 * Definition for singly-linked list.
 */
struct TreeNode {
	int val;
	struct TreeNode *left;
	struct TreeNode *right;
};

/* Solution */
int maxDepth(struct TreeNode* root) {
	if (NULL == root)
		return 0;
	int ld = maxDepth(root->left) + 1;
	int rd = maxDepth(root->right) + 1;
	return ld > rd ? ld : rd;
}

/* Test Case */
int main() {
}
