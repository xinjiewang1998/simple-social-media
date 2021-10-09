package com.example.myapplication.search.BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * B+Tree
 * @param <T> the generic type this BTree uses. It extends comparable
 *            which allows us to order two of the same type.
 */
public class BTree<T extends Comparable<T>> {
    /**
     * Fields of a BTree.
     * <p>
     * Notice that we keep track of the root of the BTree. This is because
     * of the nature of the BTree. It grows upwards! Unless you can return the
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

        List<BNode<T>> path =  new ArrayList<BNode<T>>();
        root.insert(key, path);   // Feel free to replace this.

        for (int i = path.size()-1; i > 0; i--) {
            if (path.get(i).keys.size() == order) {
                split(path.get(i-1), path.get(i));
            }
        }

        if(root.keys.size() == order) {
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
     * <p>
     * Here's a question: why could we not run '.split()' without inputs on the node we want to split?
     * Answer: We actually can! This is just one of many design decisions you may have to consider when working
     * on a project. In this case, if this split method was in BTreeNode and used '.split()' without inputs
     * on the node you want to split, you would need to have a reference to the parent of the current BTreeNode.
     * We chose not to pursue this. Please do not move this method into BTreeNode to prevent complications.
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


        // not leaf
        if(childToSplit instanceof BTreeNode) {
            BTreeNode<T> leftNode = new BTreeNode<>(order);
            BTreeNode<T> rightNode = new BTreeNode<>(order);

            leftNode.keys = childToSplit.keys.get(0, med);
            rightNode.keys = childToSplit.keys.get(med+1, order);
            leftNode.children = ((BTreeNode<T>) childToSplit).children.get(0, med + 1);
            rightNode.children = ((BTreeNode<T>) childToSplit).children.get(med + 1, order + 1);

            boolean found = false;
            int i = 0;
            for (; i < node.keys.size(); i++) {
                if(medValue.compareTo(node.keys.get(i)) <= 0) {
                    node.keys.add(i, medValue);
                    found = true;
                    break;
                }
            }

            if(!found) {
                node.keys.add(medValue);
            }

            ((BTreeNode<T>)node).children.remove(childToSplit);
            ((BTreeNode<T>)node).children.add(i, leftNode);
            ((BTreeNode<T>)node).children.add(i+1, rightNode);

        } else {

            BLeafNode<T> leftNode = new BLeafNode<>(order);
            BLeafNode<T> rightNode = new BLeafNode<>(order);
            leftNode.keys = childToSplit.keys.get(0, med+1);
            rightNode.keys = childToSplit.keys.get(med+1, order);

            leftNode.prev = ((BLeafNode<T>) childToSplit).prev;
            leftNode.next = rightNode;
            rightNode.prev = leftNode;
            rightNode.next = ((BLeafNode<T>) childToSplit).next;

            boolean found = false;
            int i = 0;
            for (; i < node.keys.size(); i++) {
                if(medValue.compareTo(node.keys.get(i)) <= 0) {
                    node.keys.add(i, medValue);
                    found = true;
                    break;
                }
            }

            if(!found) {
                node.keys.add(medValue);
            }

            ((BTreeNode<T>)node).children.remove(childToSplit);
            ((BTreeNode<T>)node).children.add(i, leftNode);
            ((BTreeNode<T>)node).children.add(i+1, rightNode);
        }
    }

    /**
     * Adds element in ascending order.
     * Helper function for insert.
     * Warning: may result in IndexOutOfBoundsException if you add too many.
     *
     * @param list    to add element into.
     * @param element to add into list.
     */
    private void addInOrder(LimitedArrayList<T> list, T element) {
        // Ensure inputs are not null.
        if (list == null || element == null)
            throw new IllegalArgumentException("Input cannot be null");

        // Go through each index and check if the element being inserted is less than the current element being looked at.
        for (int i = 0; i < list.size() && i < list.getCapacity(); i++) {
            // If less, insert it at that index (pushing all other elements forward by 1).
            if (element.compareTo(list.get(i)) < 0) {
                list.add(i, element);
                return;
            }
        }
        // If nothing is greater than the element being inserted, than it must be the greatest element. Insert at end.
        list.add(element);
    }

    /**
     * @return maximum key of the BTree
     */
    public T max() {
        return root.max();
    }

    /**
     * @return minimum key of the BTree
     */
    public T min() {
        return root.min();
    }

    /**
     * Translates the class into something human readable.
     * DO NOT EDIT THIS. Your tests rely on the toString() method.
     */
    @Override
    public String toString() {
        return "{" +
                "order=" + order +
                ", root=" + root +
                '}';
    }

    /**
     * @return Graphical representation of the tree.
     */
//    public String display() {
//        return this.root.display(0);
//    }
}
