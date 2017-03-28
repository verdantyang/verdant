/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(n)
 */

/**
 * Definition for a binary tree node.
 */
struct TreeNode {
	int val;
	struct TreeNode *left;
	struct TreeNode *right;

};

struct TreeNode* lowestCommonAncestor(struct TreeNode* root, struct TreeNode* p, struct TreeNode* q) {
	int bigger = p->val >= q->val ? p->val : q->val;
	int smaller = p->val >= q->val ? q->val : p->val;
	if (bigger == smaller)
		return p;
	if (root->val >= smaller && root->val <= bigger)
		return root;
	else if (root->val < smaller)
		return lowestCommonAncestor(root->right, p, q);
	else if (root->val > bigger)
		return lowestCommonAncestor(root->left, p, q);
}

/* Test Case */
int main() {
}