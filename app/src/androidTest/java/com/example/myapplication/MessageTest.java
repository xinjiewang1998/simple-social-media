package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MessageTest {
    // This test is for the activity_start.xml and StartActivity.java
    @Rule
    public ActivityTestRule<MessageActivity> activityRule =
            new ActivityTestRule<>(MessageActivity.class);

    // This method test whether items is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.portrait)).check(matches(isDisplayed()));
        onView(withId(R.id.user_name)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withText(R.string.login)).check(matches(isDisplayed()));
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withText(R.string.register)).check(matches(isDisplayed()));
    }

    // This method test whether items is on correct position.
    @Test
    public void positionUITest() {
        onView(withId(R.id.portrait)).check(isCompletelyAbove(withId(R.id.user_name)));
        onView(withId(R.id.user_name)).check(isCompletelyAbove(withId(R.id.password)));
        onView(withId(R.id.password)).check(isCompletelyAbove(withId(R.id.login_button)));
        onView(withId(R.id.password)).check(isCompletelyAbove(withId(R.id.register_button)));
        onView(withId(R.id.login_button)).check(isCompletelyLeftOf(withId(R.id.register_button)));
        onView(withId(R.id.register_button)).check(isCompletelyRightOf(withId(R.id.login_button)));
    }

    // This method test whether button is click or not.
    @Test
    public void buttonTest() {
        onView(withId(R.id.login_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.register_button)).perform(click()).check(matches(isClickable()));
    }
}
