package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyLeftOf;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.chat.ChatBoxActivity;

import org.junit.Rule;
import org.junit.Test;

public class ChatBoxActivityTest111 {
    @Rule
    public androidx.test.rule.ActivityTestRule<ChatBoxActivity> ActivityTestRule =
            new ActivityTestRule<>(ChatBoxActivity.class);

    @Test
    public void chatBoxUITest() {
        onView(withId(R.id.friend_name)).check(isLeftOf(withId(R.id.pic)));
        onView(withId(R.id.friend_name)).check(isPartiallyLeftOf(withId(R.id.pic)));
        onView(withId(R.id.friend_name)).check(isCompletelyLeftOf(withId(R.id.pic)));
    }
}
