package com.example.myapplication.search;

public class IndexPostObj implements Comparable<IndexPostObj> {

    PostObj postObj;
    String index;

    public IndexPostObj(PostObj postObj, String index) {
        this.postObj = postObj;
        this.index = index;
    }

    /**
     * compare by index, used in b+tree
     * @param indexPostObj the index - post obj
     * @return greater or smaller
     */
    @Override
    public int compareTo(IndexPostObj indexPostObj) {
        return this.index.compareTo(indexPostObj.index);
    }
}