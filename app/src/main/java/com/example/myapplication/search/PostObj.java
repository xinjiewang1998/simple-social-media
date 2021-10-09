package com.example.myapplication.search;

public class PostObj{

    String image_url;
    int comment_count;
    int like_count;
    String text;

    public PostObj(String image_url, int comment_count, int like_count, String text) {
        this.image_url = image_url;
        this.comment_count = comment_count;
        this.like_count = like_count;
        this.text = text;
    }
}