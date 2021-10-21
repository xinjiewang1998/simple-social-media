package com.example.myapplication.search.BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the nodes that make up the BTree.
 * You are free to add helper methods to this class to help you accomplish the tasks.
 */
class BTreeNode<T extends Comparable<T>> extends BNode<T> {
    /**
     * Fields of the node class.
     */
    LimitedArrayList<BNode<T>> children;   // Children of the node.

    /**
     * Constructor which initialises the fields.
     * <p>
     * (Readability tip: you can minimise methods using the minimise icon on the left of the method. This will help
     * clear the class for better readability).
     */
    public BTreeNode(int order) {
        super(order);
        // The limit on children is actually 'order' but at times we will go over by 1 in which we will need to split.
        this.children = new LimitedArrayList<>(order + 1);
    }

    /**
     * Adds a key to the node. Splitting if necessary.
     *
     * @param key to be inserted.
     */
    @Override
    public void insert(T key, List<BNode<T>> path) {
        // Ensure input is not null.
        if (key == null)
            throw new IllegalArgumentException("Input cannot be null");

        path.add(this);

        for (int i = 0; i < this.keys.size(); i++) {
            if (key.compareTo(this.keys.get(i)) <= 0) {
                this.children.get(i).insert(key, path);
                return;
            }
        }
        this.children.get(this.children.size() - 1).insert(key, path);

    }

    @Override
    public List<T> get(T key) {
        if (key == null) {
            return null;
        }
        // Check if leaf
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keys.get(i).compareTo(key) >= 0) {
                return this.children.get(i).get(key);
            }
        }
        return this.children.get(this.children.size() - 1).get(key);
    }

    /**
     * @return maximum key of the BTree
     */
    @Override
    public T max() {
        return this.children.get(this.children.size() - 1).max();
    }

    /**
     * @return minimum key of the BTree
     */
    @Override
    public T min() {
        return this.children.get(0).min();
    }

    /**
     * Translates the class into something human readable.
     * <p>
     * DO NOT EDIT THIS. Your tests rely on the toString() method.
     */
    @Override
    public String toString() {
        return "{" +
                "keys=" + keys +
                ", children=" + children +
                '}';
    }

}

