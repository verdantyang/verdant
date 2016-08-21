package Tree.P104_MaximumDepthOfBinaryTree;

public class Solution104 {
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int ld = maxDepth(root.left) + 1;
        int rd = maxDepth(root.right) + 1;
        return ld > rd ? ld : rd;
    }
}