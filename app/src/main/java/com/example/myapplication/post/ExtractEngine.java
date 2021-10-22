package com.example.myapplication.post;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractEngine {
    // Regex
    private static final String HASHTAG_REGEX = "#[a-zA-Z0-9]+\\b";
    private static final String USERNAME_REGEX = "@[a-zA-Z0-9]+\\b";

    /**
     * Extract tags(#) from given String text.
     *
     * @param textString content of the post
     * @return A list contains all tags.
     */
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
            tags.add("#NoTag");
        }
        return tags;
    }

    /**
     * Extract user name(@) from given String text.
     *
     * @param textString content of the post
     * @return A list contains all user name.
     */
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
        if (userNames.size() == 0) {
            userNames.add("@Unknown user");
        }
        return userNames;
    }
}
