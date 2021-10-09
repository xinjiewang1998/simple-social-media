package com.example.myapplication.search.QParser;

import com.example.myapplication.search.PostObj;
import com.example.myapplication.search.StoreEngine;

import java.util.List;

public class TagExp extends Exp {

    private String value;
    private List<PostObj> list;


    public TagExp(String value, StoreEngine se) {
        this.value = value;
        this.list = se.queryTag(this.value);
    }

    @Override
    public String show() {
        return this.list.toString();
    }

    @Override
    public List<PostObj> evaluate() {
        return this.list;
    }

}
