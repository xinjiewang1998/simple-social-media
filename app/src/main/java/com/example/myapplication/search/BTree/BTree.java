package com.example.myapplication.search.BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * B+Tree
 *
 * @param <T> the generic type this B+Tree uses. It extends comparable
 *            which allows us to order two of the same type.
 */
public class BTree<T extends Comparable<T>> {
    /**
     * Fields of a B+Tree.
     * <p>
     * Notice that we keep track of the root of the B+Tree. This is because
     * of the nature of the B+Tree. It grows upwards! Unless you can return the
     * root each time (which is a possible implementation approach) you will need
     * to keep track of the root.
     */
    private int order;          // Order of the BTree.
    private BNode<T> root;     // Root node of the BTree.

    /**
     * Constructor which sets the field 'order'
     *
     * @param order of the BTree.
     */
    public BTree(int order) {
        this.order = order;
        root = new BLeafNode<T>(order);
    }

    /**
     * Adds key to the BTree.
     *
     * @param key to be inserted.
     */
    public void insert(T key) {
        // Ensure input is not null.
        if (key == null)
            throw new IllegalArgumentException("Input cannot be null");

        List<BNode<T>> path = new ArrayList<BNode<T>>();
        root.insert(key, path);

        // split if required
        for (int i = path.size() - 1; i > 0; i--) {
            if (path.get(i).keys.size() == order) {
                split(path.get(i - 1), path.get(i));
            }
        }

        // split root if required
        if (root.keys.size() == order) {
            BTreeNode<T> newRoot = new BTreeNode<>(order);
            split(newRoot, root);
            root = newRoot;
        }
    }

    public List<T> get(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (root != null) {
            return root.get(key);
        }
        return null;
    }

    /**
     * Will conduct a split on the provided indexed child of the provided node.
     *
     * @param node         The current node whose provided child to split will be split.
     * @param childToSplit The child to split within the provided node.
     */
    private void split(BNode<T> node, BNode<T> childToSplit) {

        // Ensure neither input is null.
        if (node == null || childToSplit == null)
            throw new IllegalArgumentException("Input cannot be null");

        // Get median key
        int med = childToSplit.keys.size() / 2;
        T medValue = childToSplit.keys.get(med);


        if (childToSplit instanceof BTreeNode) {
            // not leaf
            BTreeNode<T> leftNode = new BTreeNode<>(order);
            BTreeNode<T> rightNode = new BTreeNode<>(order);

            leftNode.keys = childToSplit.keys.get(0, med);
            rightNode.keys = childToSplit.keys.get(med + 1, order);
            leftNode.children = ((BTreeNode<T>) childToSplit).children.get(0, med + 1);
            rightNode.children = ((BTreeNode<T>) childToSplit).children.get(med + 1, order + 1);

            boolean found = false;
            int i = 0;
            for (; i < node.keys.size(); i++) {
                if (medValue.compareTo(node.keys.get(i)) <= 0) {
                    node.keys.add(i, medValue);
                    found = true;
                    break;
                }
            }

            if (!found) {
                node.keys.add(medValue);
            }

            ((BTreeNode<T>) node).children.remove(childToSplit);
            ((BTreeNode<T>) node).children.add(i, leftNode);
            ((BTreeNode<T>) node).children.add(i + 1, rightNode);

        } else {
            // leaf node
            BLeafNode<T> leftNode = new BLeafNode<>(order);
            BLeafNode<T> rightNode = new BLeafNode<>(order);
            leftNode.keys = childToSplit.keys.get(0, med + 1);
            rightNode.keys = childToSplit.keys.get(med + 1, order);

            // reset connections
            leftNode.prev = ((BLeafNode<T>) childToSplit).prev;
            leftNode.next = rightNode;
            rightNode.prev = leftNode;
            rightNode.next = ((BLeafNode<T>) childToSplit).next;

            boolean found = false;
            int i = 0;
            for (; i < node.keys.size(); i++) {
                if (medValue.compareTo(node.keys.get(i)) <= 0) {
                    node.keys.add(i, medValue);
                    found = true;
                    break;
                }
            }

            if (!found) {
                node.keys.add(medValue);
            }

            ((BTreeNode<T>) node).children.remove(childToSplit);
            ((BTreeNode<T>) node).children.add(i, leftNode);
            ((BTreeNode<T>) node).children.add(i + 1, rightNode);
        }
    }

    /**
     * @return maximum key of the B+Tree
     */
    public T max() {
        return root.max();
    }

    /**
     * @return minimum key of the B+Tree
     */
    public T min() {
        return root.min();
    }

    /**
     * Translates the class into something human readable.
     */
    @Override
    public String toString() {
        return "{" +
                "order=" + order +
                ", root=" + root +
                '}';
    }
}
