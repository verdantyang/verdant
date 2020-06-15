package Tree.P653_TwoSumIV_InputisaBST;

import struct.TreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Data Structures:   Set
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(log n)
 */
public class Solution653 {
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> pair = new HashSet<>();
        return traverse(root, pair, k);
    }

    public boolean traverse(TreeNode root, Set<Integer> pair, int k) {
        if (root == null)
            return false;
        if (pair.contains(root.val))
            return true;
        else
            pair.add(k - root.val);
        return traverse(root.left, pair, k) || traverse(root.right, pair, k);
    }
}
