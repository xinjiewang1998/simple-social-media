package com.example.myapplication.search.BTree;

import java.util.ArrayList;
import java.util.List;

class BLeafNode<T extends Comparable<T>> extends BNode<T> {

    BLeafNode<T> prev;
    BLeafNode<T> next;

    public BLeafNode(int order, BLeafNode<T> prev, BLeafNode<T> next) {
        super(order);
        this.prev = prev;
        this.next = next;
    }

    public BLeafNode(int order) {
        super(order);
        this.prev = null;
        this.next = null;
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
                this.keys.add(i, key);
                return;
            }
        }
        this.keys.add(key);
    }

    @Override
    public List<T> get(T key) {
        List<T> keys = new ArrayList<>();
        BLeafNode<T> current = this;
        while(current != null) {
            List<T> newKeys = current.simpleGet(key);
            keys.addAll(newKeys);
            if(current.keys.size() != newKeys.size()) {
                break;
            }
            current = current.next;
        }
        return keys;
    }


    public List<T> simpleGet(T key) {
        if (key == null) {
            return null;
        }
        List<T> keys = new ArrayList<>();

        for (T t : this.keys) {
            if (t.compareTo(key) == 0) {
                keys.add(t);
            }
        }
        return keys;
    }

    @Override
    public T max() {
        if (this.keys.size() != 0) {
            return this.keys.get(this.keys.size() - 1);
        }
        return null;
    }

    @Override
    public T min() {
        if (this.keys.size() != 0) {
            return this.keys.get(0);
        }
        return null;
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
                '}';
    }

    /**
     * Graphically visualises the tree for human readability.
     *
     * @param tabs from the left side of the screen.
     * @return graph of the tree.
     */
//    @Override
//    public String display(int tabs) {
//        // StringBuilder is faster than using string concatenation (which in java makes a new object per concatenation).
//        StringBuilder sb = new StringBuilder(keys.toString());
//        return sb.toString();
//    }
}
