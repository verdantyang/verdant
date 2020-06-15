package Tree.P538_ConvertBSTtoGreaterTree;

import struct.TreeNode;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(nlogn) ~ O(n^2)
 * @Space Complexity:  O(n)
 */
public class Solution538 {
    public TreeNode convertBST(TreeNode root) {
        calBST(root, 0);
        return root;
    }

    /**
     * get the leftmost node val
     * @param node
     * @param base
     * @return
     */
    private int calBST(TreeNode node, int base) {
        if (node == null)
            return base;
        node.val += calBST(node.right, base);
        return calBST(node.left, node.val);
    }
}