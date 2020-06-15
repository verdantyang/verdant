package Tree.P617_MergeTwoBinaryTrees;

import struct.TreeNode;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(nlogn) ~ O(n^2)
 * @Space Complexity:  O(n)
 */
public class Solution617 {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        TreeNode newTree = new TreeNode(t1.val + t2.val);
        newTree.left = mergeTrees(t1.left, t2.left);
        newTree.right = mergeTrees(t1.right, t2.right);
        return newTree;
    }
}