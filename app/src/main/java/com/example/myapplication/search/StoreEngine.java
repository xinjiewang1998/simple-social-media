package com.example.myapplication.search;


import com.example.myapplication.search.BTree.BTree;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreEngine {

    private static final String HASHTAG_REGEX = "#[a-zA-Z0-9]+\\b";
    private static final String USERNAME_REGEX = "@[a-zA-Z0-9]+\\b";
    private static final int ORDER = 20;


    BTree<IndexPostObj> tagTree;
    BTree<IndexPostObj> userTree;
    List<PostObj> postObjs;

    public StoreEngine(List<PostObj> postObjList) {
        //postObjs = extractPostObj(jsonString);
        postObjs = postObjList;
        tagTree = extractTagPostObj(postObjs);
        userTree = extractUserPostObj(postObjs);
    }

    public List<PostObj> extractPostObj(String jsonString) {

        List<PostObj> postObjs = new ArrayList<PostObj>();
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray posts = (JSONArray) json.get("allpost");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = (JSONObject) posts.get(i);
                String imageUrl = post.getString("img_url");
                int commentCount = post.getInt("comment_count");
                int likeCount = post.getInt("like_count");
                String text = post.getString("text");
                PostObj postObj = new PostObj(imageUrl, commentCount, likeCount, text);
                postObjs.add(postObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return postObjs;
    }


    public BTree<IndexPostObj> extractTagPostObj(List<PostObj> postObjs) {
        Pattern pattern = Pattern.compile(HASHTAG_REGEX);
        BTree<IndexPostObj> bTree = new BTree<>(ORDER);
        for (PostObj postObj : postObjs) {
            List<String> indexes = new ArrayList<String>();
            boolean matchFound = true;
            String textString = postObj.text;
            while (matchFound && !textString.equals("")) {
                Matcher matcher = pattern.matcher(textString);
                matchFound = matcher.find();
                if (matchFound) {
                    String index = matcher.group(0);
                    if (!indexes.contains(index))
                        indexes.add(index);
                    textString = textString.substring(matcher.end());
                }
            }
            if (indexes.size() != 0) {
                for (String index : indexes) {
                    bTree.insert(new IndexPostObj(postObj, index));
                }
            } else {
                bTree.insert(new IndexPostObj(postObj, "#NoTag"));
            }
        }
        return bTree;
    }

    public BTree<IndexPostObj> extractUserPostObj(List<PostObj> postObjs) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        BTree<IndexPostObj> bTree = new BTree<>(ORDER);
        for (PostObj postObj : postObjs) {
            List<String> indexes = new ArrayList<String>();
            boolean matchFound = true;
            String textString = postObj.text;
            while (matchFound && !textString.equals("")) {
                Matcher matcher = pattern.matcher(textString);
                matchFound = matcher.find();
                if (matchFound) {
                    String index = matcher.group(0);
                    if (!indexes.contains(index))
                        indexes.add(index);
                    textString = textString.substring(matcher.end());
                }
            }
            if (indexes.size() != 0) {
                for (String index : indexes) {
                    bTree.insert(new IndexPostObj(postObj, index));
                }
            } else {
                bTree.insert(new IndexPostObj(postObj, "@NoUser"));
            }
        }
        return bTree;
    }

    public List<PostObj> queryPost() {
        return this.postObjs;
    }

    public int queryNumPosts() {
        return this.postObjs.size();
    }

    public List<PostObj> queryTag(String tag) {
        List<PostObj> returnedPostObjs = new ArrayList<>();
        List<IndexPostObj> indexPostObjs = this.tagTree.get(new IndexPostObj(null, tag));
        for (IndexPostObj indexPostObj : indexPostObjs) {
            returnedPostObjs.add(indexPostObj.postObj);
        }
        return returnedPostObjs;
    }

    public List<PostObj> queryUser(String user) {
        List<PostObj> returnedPostObjs = new ArrayList<>();
        List<IndexPostObj> indexPostObjs = this.userTree.get(new IndexPostObj(null, user));
        for (IndexPostObj indexPostObj : indexPostObjs) {
            returnedPostObjs.add(indexPostObj.postObj);
        }
        return returnedPostObjs;
    }

    public String queryMinTag() {
        return this.tagTree.min().index;
    }

    public String queryMaxTag() {
        return this.tagTree.max().index;
    }

    public String queryMinUser() {
        return this.userTree.min().index;
    }

    public String queryMaxUser() {
        return this.userTree.max().index;
    }

}