package Tree.P235_LowestCommonAncestorOfABinarySearchTree;

import struct.TreeNode;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution235 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int bigger = p.val > q.val ? p.val : q.val;
        int smaller = p.val < q.val ? p.val : q.val;
        if (bigger == smaller)
            return p;
        if (root.val <= bigger && root.val >= smaller)
            return root;
        else if (root.val < smaller)
            return lowestCommonAncestor(root.right, p, q);
        else if (root.val > bigger)
            return lowestCommonAncestor(root.left, p, q);
        return null;
    }
}