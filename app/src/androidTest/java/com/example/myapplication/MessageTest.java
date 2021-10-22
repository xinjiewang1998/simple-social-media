package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MessageTest {

    /* Must execute after the StartActivityLoginTest*/

    // This test is for the activity_message.xml and MessageActivity.
    @Rule
    public IntentsTestRule<MessageActivity> activityRule =
            new IntentsTestRule<>(MessageActivity.class);

    // This method test whether items is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withText("User list")).check(matches(isDisplayed()));
        onView(withId(R.id.list_friends)).check(matches(isDisplayed()));
    }

    // This method test whether items is on correct position.
    @Test
    public void positionUITest() {
        onView(withId(R.id.textView)).check(isCompletelyAbove(withId(R.id.list_friends)));
        onView(withId(R.id.list_friends)).check(isCompletelyBelow(withText("User list")));
    }

    // Check the whether AllPostView can be scroll.
    @Test
    public void recycleViewTest(){
        onView(withId(R.id.list_friends))
                .perform(RecyclerViewActions
                        .scrollToPosition(0));
    }

}
