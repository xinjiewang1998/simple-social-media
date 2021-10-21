package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.post.eachpost;

import org.junit.Rule;
import org.junit.Test;

public class EachPostTest {
    @Rule
    public ActivityTestRule<eachpost> ActivityTestRule =
            new ActivityTestRule<>(eachpost.class);

    @Test
    public void eachPost() {
        onView(withId(R.id.good)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.share)).perform(click()).check(matches(isClickable()));

    }
}
