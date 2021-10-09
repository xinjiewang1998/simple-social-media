package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MessageTest {
    @Rule
    public ActivityTestRule<MessageActivity> mActivityTestRule = new ActivityTestRule<>(MessageActivity.class);

    @Test
    public void messageActivityTest() {
        onView(withId(R.id.textView2)).check(matches(withText("  Like")));
        onView(withId(R.id.reply)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.like)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.account_name)).check(matches(withText("TextView")));
    }
}
