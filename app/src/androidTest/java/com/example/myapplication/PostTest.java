package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class PostTest {
    @Rule
    public ActivityTestRule<PostActivity> activityRule =
            new ActivityTestRule<>(PostActivity.class);

    @Test
    public void postUITest() {
        onView(withId(R.id.searchBox)).check(isAbove(withId(R.id.concerned)));
//        onView(withId(R.id.account_name)).check(isPartiallyAbove(withId(R.id.hot)));
        onView(withId(R.id.account_name)).check(isCompletelyAbove(withId(R.id.all)));

        onView(withId(R.id.hot)).check(isPartiallyRightOf(withId(R.id.concerned)));
        onView(withId(R.id.hot)).check(isCompletelyRightOf(withId(R.id.concerned)));
        onView(withId(R.id.hot)).check(isPartiallyLeftOf(withId(R.id.all)));
        onView(withId(R.id.hot)).check(isCompletelyLeftOf(withId(R.id.all)));

        onView(withId(R.id.all)).check(isCompletelyAbove(withId(R.id.hottest)));
        onView(withId(R.id.all)).check(isPartiallyAbove(withId(R.id.hottest)));

        onView(withId(R.id.hottest)).check(isCompletelyAbove(withId(R.id.imageView4)));
        onView(withId(R.id.hottest)).check(isPartiallyAbove(withId(R.id.imageView4)));

        onView(withId(R.id.imageView4)).check(isCompletelyAbove(withId(R.id.post_list)));
        onView(withId(R.id.imageView4)).check(isPartiallyAbove(withId(R.id.post_list)));
    }

    @Test
    public void postTest() {
        onView(withId(R.id.concerned)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.hot)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.all)).perform(click()).check(matches(isClickable()));
    }
}
