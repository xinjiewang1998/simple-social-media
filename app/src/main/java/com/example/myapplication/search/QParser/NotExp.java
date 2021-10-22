package com.example.myapplication.search.QParser;

import com.example.myapplication.search.PostObj;
import com.example.myapplication.search.StoreEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotExp extends Exp {
    private Exp factor;
    private List<PostObj> list;


    public NotExp(Exp factor, StoreEngine se) {
        this.factor = factor;
        this.list = se.queryPost();
    }

    @Override
    public String show() {
        return "( !" + factor.show() + ")";
    }

    /**
     * not in exp
     * world - set for not
     *
     * @return list of post obj
     */
    @Override
    public List<PostObj> evaluate() {
        // NOT
        Set<PostObj> s1 = new HashSet<>(this.list);
        s1.removeAll(factor.evaluate());
        return new ArrayList<>(s1);
    }
}