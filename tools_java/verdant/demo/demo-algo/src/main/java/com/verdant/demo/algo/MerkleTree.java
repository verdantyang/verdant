package com.verdant.demo.algo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;

/**
 * MerkleTree
 *
 * @author verdant
 * @since 2016/07/18
 */
public class MerkleTree {

    public static final byte LEAF_SIG_TYPE = 0x0;
    public static final byte INTERNAL_SIG_TYPE = 0x01;

    private final Adler32 crc = new Adler32();
    private List<String> leafSigs;
    private Node root;
    private HashType hashType;
    private int depth;
    private int nnodes;


    /**
     * Use this constructor to create a MerkleTree from a list of leaf signatures.
     * The Merkle tree is built from the bottom up.
     *
     * @param leafSignatures
     */
    public MerkleTree(List<String> leafSignatures) {
        constructTree(leafSignatures);
    }

    /**
     * Use this constructor when you have already constructed the tree of Nodes
     * (from deserialization).
     *
     * @param root
     * @param nnodes
     * @param depth
     * @param leafSigs
     */
    public MerkleTree(Node root, int nnodes, int depth, List<String> leafSigs) {
        this.root = root;
        this.nnodes = nnodes;
        this.depth = depth;
        this.leafSigs = leafSigs;
    }


    /**
     * Create a tree from the bottom up starting from the leaf signatures.
     *
     * @param signatures
     */
    void constructTree(List<String> signatures) {
        if (signatures.size() <= 1) {
            throw new IllegalArgumentException("Must be at least two signatures to construct a Merkle tree");
        }

        leafSigs = signatures;
        nnodes = signatures.size();
        List<Node> parents = bottomLevel(signatures);
        nnodes += parents.size();
        depth = 1;

        while (parents.size() > 1) {
            parents = internalLevel(parents);
            depth++;
            nnodes += parents.size();
        }

        root = parents.get(0);
    }


    public int getNumNodes() {
        return nnodes;
    }

    public Node getRoot() {
        return root;
    }

    public int getDepth() {
        return depth;
    }


    /**
     * Constructs an internal level of the tree
     */
    List<Node> internalLevel(List<Node> children) {
        List<Node> parents = new ArrayList<Node>(children.size() / 2);

        for (int i = 0; i < children.size() - 1; i += 2) {
            Node child1 = children.get(i);
            Node child2 = children.get(i + 1);

            Node parent = constructInternalNode(child1, child2);
            parents.add(parent);
        }

        if (children.size() % 2 != 0) {
            Node child = children.get(children.size() - 1);
            Node parent = constructInternalNode(child, null);
            parents.add(parent);
        }

        return parents;
    }


    /**
     * Constructs the bottom part of the tree - the leaf nodes and their
     * immediate parents.  Returns a list of the parent nodes.
     */
    List<Node> bottomLevel(List<String> signatures) {
        List<Node> parents = new ArrayList<Node>(signatures.size() / 2);

        for (int i = 0; i < signatures.size() - 1; i += 2) {
            Node leaf1 = constructLeafNode(signatures.get(i));
            Node leaf2 = constructLeafNode(signatures.get(i + 1));

            Node parent = constructInternalNode(leaf1, leaf2);
            parents.add(parent);
        }

        // if odd number of leafs, handle last entry
        if (signatures.size() % 2 != 0) {
            Node leaf = constructLeafNode(signatures.get(signatures.size() - 1));
            Node parent = constructInternalNode(leaf, null);
            parents.add(parent);
        }

        return parents;
    }

    private Node constructInternalNode(Node child1, Node child2) {
        Node parent = new Node();
        parent.type = INTERNAL_SIG_TYPE;

        if (child2 == null) {
            parent.sig = child1.sig;
        } else {
            parent.sig = internalHash(child1.sig, child2.sig);
        }

        parent.left = child1;
        parent.right = child2;
        return parent;
    }

    private Node constructLeafNode(String signature) {
        Node leaf = new Node();
        leaf.type = LEAF_SIG_TYPE;
        leaf.sig = signature.getBytes(StandardCharsets.UTF_8);
        return leaf;
    }

    byte[] internalHash(byte[] leftChildSig, byte[] rightChildSig) {
        crc.reset();
        crc.update(leftChildSig);
        crc.update(rightChildSig);
        return longToByteArray(crc.getValue());
    }


  /* ---[ Node class ]--- */

    /**
     * The Node class should be treated as immutable, though immutable
     * is not enforced in the current design.
     * <p>
     * A Node knows whether it is an internal or leaf node and its signature.
     * <p>
     * Internal Nodes will have at least one child (always on the left).
     * Leaf Nodes will have no children (left = right = null).
     */
    public static class Node {
        public byte type;  // INTERNAL_SIG_TYPE or LEAF_SIG_TYPE
        public byte[] sig; // signature of the node
        public Node left;
        public Node right;

        @Override
        public String toString() {
            String leftType = "<null>";
            String rightType = "<null>";
            if (left != null) {
                leftType = String.valueOf(left.type);
            }
            if (right != null) {
                rightType = String.valueOf(right.type);
            }
            return String.format("MerkleTree.Node<type:%d, sig:%s, left (type): %s, right (type): %s>",
                    type, sigAsString(), leftType, rightType);
        }

        private String sigAsString() {
            StringBuffer sb = new StringBuffer();
            sb.append('[');
            for (int i = 0; i < sig.length; i++) {
                sb.append(sig[i]).append(' ');
            }
            sb.insert(sb.length() - 1, ']');
            return sb.toString();
        }
    }

    /**
     * Big-endian conversion
     */
    public static byte[] longToByteArray(long value) {
        return new byte[]{
                (byte) (value >> 56),
                (byte) (value >> 48),
                (byte) (value >> 40),
                (byte) (value >> 32),
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }
}
