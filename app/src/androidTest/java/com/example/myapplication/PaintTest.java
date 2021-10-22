package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.graphics.Paint;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class PaintTest {
    /* Must execute after the StartActivityLoginTest*/

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<PaintActivity> mIntentsRule =
            new IntentsTestRule<>(PaintActivity.class);

    // Check whether the items are displayed on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.draw_view)).check(matches(isDisplayed()));

        onView(withId(R.id.brush_button)).check(matches(isDisplayed()));
        onView(withId(R.id.camera_button)).check(matches(isDisplayed()));
        onView(withId(R.id.erase_button)).check(matches(isDisplayed()));
        onView(withId(R.id.undo_button)).check(matches(isDisplayed()));

        onView(withId(R.id.blue_button)).check(matches(isDisplayed()));
        onView(withId(R.id.green_button)).check(matches(isDisplayed()));
        onView(withId(R.id.orange_button)).check(matches(isDisplayed()));
        onView(withId(R.id.pink_button)).check(matches(isDisplayed()));
        onView(withId(R.id.color_button)).check(matches(isDisplayed()));
    }

    // Check the position of items.
    @Test
    public void positionUITest() {
        onView(withId(R.id.draw_view)).check(isCompletelyAbove(withId(R.id.brush_button)));
        onView(withId(R.id.draw_view)).check(isPartiallyAbove(withId(R.id.blue_button)));

        onView(withId(R.id.camera_button)).check(isCompletelyRightOf(withId(R.id.brush_button)));
        onView(withId(R.id.undo_button)).check(isCompletelyRightOf(withId(R.id.camera_button)));
        onView(withId(R.id.erase_button)).check(isCompletelyRightOf(withId(R.id.undo_button)));

        onView(withId(R.id.color_button)).perform(click());
        onView(withId(R.id.pink_button)).check(isCompletelyAbove(withId(R.id.orange_button)));
        onView(withId(R.id.orange_button)).check(isCompletelyAbove(withId(R.id.green_button)));
        onView(withId(R.id.green_button)).check(isCompletelyAbove(withId(R.id.blue_button)));
        onView(withId(R.id.blue_button)).check(isPartiallyAbove(withId(R.id.color_button)));
    }

    // Check the button is clickable.
    @Test
    public void buttonTest() {
        onView(withId(R.id.brush_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.camera_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.erase_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.undo_button)).perform(click()).check(matches(isClickable()));

        onView(withId(R.id.blue_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.green_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.orange_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.pink_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.color_button)).perform(click()).check(matches(isClickable()));
    }
}
