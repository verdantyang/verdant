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
class Solution {
public:
	int maxDepth(TreeNode* root) {
		if (NULL == root)
			return 0;
		int ld = maxDepth(root->left) + 1;
		int rd = maxDepth(root->right) + 1;
		return ld > rd ? ld : rd;
	}
};