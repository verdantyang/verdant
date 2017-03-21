package Tree.P104_MaximumDepthOfBinaryTree;

import Tree.TreeNode;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution104 {

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int ld = maxDepth(root.left) + 1;
        int rd = maxDepth(root.right) + 1;
        return ld > rd ? ld : rd;
    }
}