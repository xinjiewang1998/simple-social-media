package com.example.myapplication.search.QParser;

import com.example.myapplication.search.PostObj;
import com.example.myapplication.search.StoreEngine;

import java.util.List;

public class UserExp extends Exp {

    private String value;
    private List<PostObj> list;

    public UserExp(String value, StoreEngine se) {
        this.value = value;
        this.list = se.queryUser(this.value);
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
