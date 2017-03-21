package Tree.P144_BinaryTreePreorderTraversal;

import Tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> cache = new Stack<>();
        List<Integer> result = new ArrayList<>();
        while (null != root || !cache.empty()) {
            if (null != root) {
                result.add(root.val);
                cache.push(root);
                root = root.left;
            } else if (!cache.empty()) {
                root = cache.pop().right;
            }
        }
        return result;
    }
}
