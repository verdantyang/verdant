package com.verdant.demo.algo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 哈希树
 *
 * @author verdant
 * @since 2016/07/18
 */
public class PersistentHashTree<K, V> {
    private static Logger logger = LoggerFactory.getLogger(PersistentHashTree.class);

    private static final int CHILD_COUNT = 32;
    private static final int MAX_LAYER = 6;

    private final AtomicReference<TreeNode> root;

    public PersistentHashTree() {
        this.root = new AtomicReference<TreeNode>(new TreeNode(null, CHILD_COUNT));
    }

    public void insert(K key, V userObj) {
        boolean success = false;
        while (!success) {
            TreeNode oldRootNode = root.get();
            TreeNode newRootNode = insertNode(oldRootNode, 1, key.hashCode(), key, userObj);
            success = root.compareAndSet(oldRootNode, newRootNode);
        }
    }

    private TreeNode insertNode(TreeNode parent, int layer, int keyHashCode, K key, V userObj) {
        int position = getNodePosition(layer, keyHashCode);
        TreeNode newParent = cloneTreeNode(parent);
        if (parent.children.length == 0 || parent.children[position] == null) {
            newParent.children[position] = createTreeLeafNode(key, userObj, null);
        } else if (parent.children[position].data == null) {
            newParent.children[position] = insertNode(parent.children[position], layer + 1, keyHashCode, key, userObj);
        } else {
            if (layer < MAX_LAYER) {
                TreeNode newChild = CreateTreeParentNode();
                newChild.children[getNodePosition(layer + 1, parent.children[position].data.key.hashCode())] = parent.children[position];
                newParent.children[position] = insertNode(newChild, layer + 1, keyHashCode, key, userObj);
            } else {
                newParent.children[position] = createTreeLeafNode(key, userObj, parent.children[position]);
            }
        }
        return newParent;
    }

    private int getNodePosition(int layer, int keyHashCode) {
        return keyHashCode >> ((layer - 1) * bitNum(CHILD_COUNT - 1)) & (CHILD_COUNT - 1);
    }

    private int bitNum(int value) {
        int num = 1;
        while ((value >> num) > 0) {
            num++;
        }
        return num;
    }

    private TreeNode cloneTreeNode(TreeNode node) {
        TreeNode newNode = new TreeNode(node.data, CHILD_COUNT);
        for (int i = 0; i < node.children.length; i++) {
            newNode.children[i] = node.children[i];
        }
        return newNode;
    }

    private TreeNode createTreeLeafNode(K key, V userObj, TreeNode oldNode) {
        NodeData data = new NodeData(key, userObj, (oldNode != null) ? oldNode.data : null);
        return new TreeNode(data, 0);
    }

    private TreeNode CreateTreeParentNode() {
        return new TreeNode(null, CHILD_COUNT);
    }

    public V get(K key) {
        return getNode(root.get(), 1, key.hashCode(), key);
    }

    private V getNode(TreeNode parent, int layer, int keyHashCode, K key) {
        int position = getNodePosition(layer, keyHashCode);
        if (parent.children.length > 0 && parent.children[position] != null) {
            if (parent.children[position].data == null) {
                return getNode(parent.children[position], layer + 1, keyHashCode, key);
            } else {
                // ���ܴ��ڳ�ͻ
                NodeData data = parent.children[position].data;
                do {
                    if (data.key.equals(key)) {
                        return data.hashValue;
                    }
                    data = data.next;
                } while (data != null);
            }
        }
        return null;
    }

    public V delete(K key) {
        boolean success = false;
        V userObj = null;
        while (!success) {
            TreeNode oldRootNode = root.get();
            userObj = getNode(oldRootNode, 1, key.hashCode(), key);
            if (userObj != null) {
                TreeNode newRootNode = deleteNode(oldRootNode, 1, key.hashCode(), key);
                if (newRootNode == null) {
                    newRootNode = CreateTreeParentNode();
                }
                success = root.compareAndSet(oldRootNode, newRootNode);
            } else {
                success = true;
            }
        }
        return userObj;
    }

    private TreeNode deleteNode(TreeNode parent, int layer, int keyHashCode, K key) {
        int position = getNodePosition(layer, keyHashCode);
        TreeNode newParent = cloneTreeNode(parent);
        if (parent.children[position].data == null) {
            newParent.children[position] = deleteNode(parent.children[position], layer + 1, keyHashCode, key);
        } else {
            NodeData data = parent.children[position].data;
            Stack<NodeData> dataStack = new Stack<>();
            while (data != null && !data.key.equals(key)) {
                dataStack.push(data);
                data = data.next;
            }
            NodeData rootData = data.next;
            while (!dataStack.empty()) {
                data = dataStack.pop();
                rootData = new NodeData(data.key, data.hashValue, rootData);
            }
            TreeNode newNode = null;
            if (rootData != null) {
                newNode = new TreeNode(rootData, 0);
            }
            newParent.children[position] = newNode;
        }
        return newParent.isNullNode() ? null : newParent;
    }

    public V modify(K key, V userObj) {
        boolean success = false;
        V oldUserObj = null;
        while (!success) {
            TreeNode oldRootNode = root.get();
            oldUserObj = getNode(oldRootNode, 1, key.hashCode(), key);
            if (oldUserObj != null) {
                TreeNode newRootNode = modifyNode(oldRootNode, 1, key.hashCode(), key, userObj);
                success = root.compareAndSet(oldRootNode, newRootNode);
            } else {
                success = true;
            }
        }
        return oldUserObj;
    }

    private TreeNode modifyNode(TreeNode parent, int layer, int keyHashCode, K key, V userObj) {
        int position = getNodePosition(layer, keyHashCode);
        TreeNode newParent = cloneTreeNode(parent);
        if (parent.children[position].data == null) {
            newParent.children[position] = modifyNode(parent.children[position], layer + 1, keyHashCode, key, userObj);
        } else {
            NodeData data = parent.children[position].data;
            Stack<NodeData> dataStack = new Stack<>();
            while (data != null && !data.key.equals(key)) {
                dataStack.push(data);
                data = data.next;
            }
            NodeData rootData = new NodeData(key, userObj, data.next);
            while (!dataStack.empty()) {
                data = dataStack.pop();
                NodeData temp = new NodeData(data.key, data.hashValue, rootData);
                rootData = temp;
            }
            TreeNode newNode = new TreeNode(rootData, 0);
            newParent.children[position] = newNode;
        }
        return newParent;
    }

    private class TreeNode {
        private final NodeData data;
        private final TreeNode[] children;

        @SuppressWarnings("unchecked")
        public TreeNode(NodeData data, int childCount) {
            this.data = data;
            this.children = new PersistentHashTree.TreeNode[childCount];
        }

        public boolean isNullNode() {
            boolean isNull = true;
            if (this.data != null) {
                isNull = false;
            } else {
                for (int i = 0; i < children.length; i++) {
                    if (children[i] != null) {
                        isNull = false;
                        break;
                    }
                }
            }
            return isNull;
        }
    }

    private class NodeData {
        private final K key;
        private final V hashValue;
        private NodeData next;

        public NodeData(K key, V hashValue, NodeData next) {
            this.key = key;
            this.hashValue = hashValue;
            this.next = next;
        }
    }

    public void print() {
        ArrayList<TreeNode> nodeList = new ArrayList<>();
        nodeList.add(root.get());
        int layer = 0;
        while (nodeList.size() > 0) {
            ArrayList<TreeNode> newList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("Layer " + layer + " : ");
            while (nodeList.size() > 0) {
                TreeNode node = nodeList.remove(0);
                NodeData data = node.data;
                if (data != null) {
                    sb.append("(" + data.hashValue + " ");
                    while (data.next != null) {
                        data = data.next;
                        System.out.print(data.hashValue + " ");
                    }
                    sb.append(")");
                }
                for (int i = 0; i < node.children.length; i++) {
                    if (node.children[i] != null) {
                        newList.add(node.children[i]);
                    }
                }
            }
            logger.info(sb.toString());
            nodeList = newList;
            layer++;
        }
    }
}
