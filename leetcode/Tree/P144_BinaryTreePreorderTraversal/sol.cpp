/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(n)
 */
#include <stdio.h>
#include <vector>
#include <stack>

using namespace std;
/**
 * Definition for a binary tree node.
 */
struct TreeNode {
	int val;
	TreeNode *left;
	TreeNode *right;
	TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

/* Solution */
class Solution {
public:
	vector<int> preorderTraversal(TreeNode* root) {
		stack<TreeNode*> cache;
		vector<int> rets;
		while (NULL != root || !cache.empty()) {
			if (NULL != root) {
				rets.push_back(root->val);
				cache.push(root);
				root = root->left;
			} else if (NULL == root && !cache.empty()) {
				root = cache.top()->right;
				cache.pop();
			}
		}
		return rets;
	}
};
