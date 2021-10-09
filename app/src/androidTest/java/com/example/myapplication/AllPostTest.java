package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class AllPostTest {
    @Rule
    public ActivityTestRule<allpost> mActivityTestRule = new ActivityTestRule<>(allpost.class);

    @Test
    public void allPost() {
        onView(withId(R.id.concern)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.Hot)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.All)).perform(click()).check(matches(isClickable()));
    }
}
