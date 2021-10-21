package com.example.myapplication.search.BTree;

import java.util.List;

public class BNode<T extends Comparable<T>> {
    LimitedArrayList<T> keys; // Keys held by the node.
    int order;

    public BNode(int order) {
        this.order = order;
        this.keys = new LimitedArrayList<>(order);
    }

    public void insert(T key, List<BNode<T>> path) {
    }

    public List<T> get(T key) {
        return null;
    }

    public T max() {
        return null;
    }

    public T min() {
        return null;
    }
}
