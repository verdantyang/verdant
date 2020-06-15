package Tree.Skeleton.P101_SymmetricTree;

import struct.TreeNode;

import java.util.Stack;

/**
 * @Data Structures:
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution101 {

    /**
     * 递归解法
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return recursion(root.left, root.right);
    }

    public boolean recursion(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;
        boolean symOuter = recursion(left.right, right.left);
        boolean symInner = recursion(left.left, right.right);
        return left.val == right.val && symOuter && symInner;
    }

    /**
     * 非递归解法
     *
     * @param root
     * @return
     */
    public boolean iteratively(TreeNode root) {
        if (root == null)
            return true;
        Stack<TreeNode> stack = new Stack<>();
        if (root.left == null || root.right == null) {
            return root.right == null && root.left == null;
        } else if (root.left.val == root.right.val) {
            stack.push(root.left);
            stack.push(root.right);
        } else {
            return false;
        }
        while (!stack.empty()) {
            TreeNode left = stack.pop();
            TreeNode right = stack.pop();

            if (left.left == null || right.right == null) {
                if (!(left.left == null && right.right == null)) {
                    return false;
                }
            } else if (left.left.val == right.right.val) {
                stack.push(left.left);
                stack.push(right.right);
            } else {
                return false;
            }

            if (left.right == null || right.left == null) {
                if (!(left.right == null && right.left == null)) {
                    return false;
                }
            } else if (left.right.val == right.left.val) {
                stack.push(left.right);
                stack.push(right.left);
            } else {
                return false;
            }
        }
        return stack.empty();
    }
}