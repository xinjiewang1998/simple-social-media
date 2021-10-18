package com.example.myapplication.search;

import com.example.myapplication.search.QParser.Parser;
import com.example.myapplication.search.QParser.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class QueryEngine {

    StoreEngine storeEngine;
    Parser parser;

    public QueryEngine(List<PostObj> postObjList) {
        this.storeEngine = new StoreEngine(postObjList);
    }


    public List<PostObj> queryObject(String query) {
        this.parser = new Parser(new Tokenizer(query), storeEngine);
        return this.parser.evaluate();
    }

    public List<String> queryText(String query) {
        this.parser = new Parser(new Tokenizer(query), storeEngine);

//        parser.printTokenizer();

        List<PostObj> list = this.parser.evaluate();
        List<String> returnedList = new ArrayList<>();
        for (PostObj postObj : list) {
            returnedList.add(postObj.text);
        }
        return returnedList;
    }
}