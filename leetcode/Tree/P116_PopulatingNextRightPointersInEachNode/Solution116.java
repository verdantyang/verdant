package Tree.P116_PopulatingNextRightPointersInEachNode;

import struct.TreeLinkNode;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution116 {

    public void connect(TreeLinkNode root) {
        if (root == null)
            return;
        TreeLinkNode current;
        while (root.left != null) {
            current = root;
            while (current != null) {
                current.left.next = current.right;
                if (current.next != null)
                    current.right.next = current.next.left;
                current = current.next;
            }
            root = root.left;
        }
    }
}