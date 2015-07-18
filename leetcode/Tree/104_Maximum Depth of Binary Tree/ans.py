# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution:
    # @param {TreeNode} root
    # @return {integer}
    def maxDepth(self, root):
    	if root == None:
            return 0
        return max(self.maxDepth(root.left) + 1, self.maxDepth(root.right) + 1);