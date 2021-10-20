package com.example.myapplication;

import com.example.myapplication.search.BTree.BTree;
import com.example.myapplication.search.IndexPostObj;
import com.example.myapplication.search.PostObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class extractEngine {
    private static final String HASHTAG_REGEX = "#[a-zA-Z0-9]+\\b";
    private static final String USERNAME_REGEX = "@[a-zA-Z0-9]+\\b";





    public static ArrayList<String> extractTag(String textString) {
        Pattern pattern = Pattern.compile(HASHTAG_REGEX);
            ArrayList<String> tags = new ArrayList<>();
            boolean matchFound = true;
            while (matchFound && !textString.equals("")) {
                Matcher matcher = pattern.matcher(textString);
                matchFound = matcher.find();
                if (matchFound) {
                    String tag = matcher.group(0);
                    if (!tags.contains(tag))
                        tags.add(tag);
                    textString = textString.substring(matcher.end());
                }
            }
            if (tags.size() == 0) {
                tags.add("NoTag");
            }

        return tags;
    }

    public static ArrayList<String> extractUserName(String textString) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        ArrayList<String> userNames = new ArrayList<>();
        boolean matchFound = true;
        while (matchFound && !textString.equals("")) {
            Matcher matcher = pattern.matcher(textString);
            matchFound = matcher.find();
            if (matchFound) {
                String userName = matcher.group(0);
                if (!userNames.contains(userName))
                    userNames.add(userName);
                textString = textString.substring(matcher.end());
            }
        }
        if(userNames.size()==0){
            userNames.add("Unknown user");
        }

        return userNames;
    }

//    public List<PostObj> queryPost() {
//        return this.postObjs;
//    }
//
//    public int queryNumPosts() {
//        return this.postObjs.size();
//    }
//
//    public List<PostObj> queryTag(String tag) {
//        List<PostObj> returnedPostObjs = new ArrayList<>();
//        List<IndexPostObj> indexPostObjs = this.tagTree.get(new IndexPostObj(null, tag));
//        for (IndexPostObj indexPostObj : indexPostObjs) {
//            returnedPostObjs.add(indexPostObj.postObj);
//        }
//        return returnedPostObjs;
//    }
//
//    public List<PostObj> queryUser(String user) {
//        List<PostObj> returnedPostObjs = new ArrayList<>();
//        List<IndexPostObj> indexPostObjs = this.userTree.get(new IndexPostObj(null, user));
//        for (IndexPostObj indexPostObj : indexPostObjs) {
//            returnedPostObjs.add(indexPostObj.postObj);
//        }
//        return returnedPostObjs;
//    }
}
