package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class StartActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void startUITest() {
        onView(withId(R.id.portrait)).check(isAbove(withId(R.id.user_name)));
        onView(withId(R.id.user_name)).check(isAbove(withId(R.id.password)));
        onView(withId(R.id.password)).check(isAbove(withId(R.id.login_button)));
        onView(withId(R.id.password)).check(isCompletelyAbove(withId(R.id.register_button)));
        onView(withId(R.id.login_button)).check(isLeftOf(withId(R.id.register_button)));
        onView(withId(R.id.register_button)).check(isRightOf(withId(R.id.login_button)));
    }

    @Test
    public void buttonTest() {
        onView(withId(R.id.login_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.register_button)).perform(click()).check(matches(isClickable()));
    }

    @Test
    public void registerTest() {
        onView(withId(R.id.user_name)).
                perform(typeText("android@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).
                perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
    }

    @Test
    public void loginTest() {
        onView(withId(R.id.user_name)).
                perform(typeText("android@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
//        onView(withText("Password is required")).check(matches(withText("Password is required")));
        onView(withId(R.id.password)).
                perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
    }

    @Test
    public void emptyPassword() {
        onView(withId(R.id.user_name)).
                perform(typeText("admin@qq.com"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
    }

    @Test
    public void emptyUser() {
        onView(withId(R.id.password)).
                perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
    }


}
