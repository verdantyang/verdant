package Tree.Traversal.P094_BinaryTreeInorderTraversal;

import struct.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution094 {

    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> cache = new Stack<>();
        List<Integer> result = new ArrayList<>();
        while (null != root || !cache.empty()) {
            if (null != root) {
                cache.push(root);
                root = root.left;
            } else if (!cache.empty()) {
                result.add(cache.peek().val);
                root = cache.pop().right;
            }
        }
        return result;
    }
}
