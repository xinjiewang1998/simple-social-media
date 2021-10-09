package com.example.myapplication.search.QParser;

import com.example.myapplication.search.PostObj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrExp extends Exp {

    private Exp term;
    private Exp exp;

    public OrExp(Exp term, Exp exp) {
        this.term = term;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + term.show() + " | " + exp.show() + ")";
    }

    @Override
    public List<PostObj> evaluate() {
        // OR
        Set<PostObj> s1 = new HashSet<>(term.evaluate());
        s1.addAll(exp.evaluate());
        return new ArrayList<>(s1);
    }
}
