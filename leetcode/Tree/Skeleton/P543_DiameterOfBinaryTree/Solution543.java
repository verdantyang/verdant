package Tree.Skeleton.P543_DiameterOfBinaryTree;

import struct.TreeNode;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution543 {

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        int dia = maxDepth(root.left) + maxDepth(root.right);
        int left = diameterOfBinaryTree(root.left);
        int right = diameterOfBinaryTree(root.right);
        return Math.max(dia, Math.max(left, right));
    }

    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
}