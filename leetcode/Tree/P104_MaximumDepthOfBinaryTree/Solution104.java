package Tree.P104_MaximumDepthOfBinaryTree;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution104 {
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