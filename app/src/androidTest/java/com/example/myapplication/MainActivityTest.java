package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    /*
    * Must execute after the StartActivityLoginTest
    *
    * This class test MainActivity, and the intent from MainActivity to Message.Activity,
    * Paint.Activity, AllPostPage.Activity.
    * */

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule =
            new IntentsTestRule<>(MainActivity.class);

    // This method test whether items is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.roundedImageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.line1)).check(matches(isDisplayed()));

        onView(withId(R.id.message_Image)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withText("Message")).check(matches(isDisplayed()));
        onView(withId(R.id.message_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.line2)).check(matches(isDisplayed()));

        onView(withId(R.id.all_post_image)).check(matches(isDisplayed()));
        onView(withId(R.id.my_post)).check(matches(isDisplayed()));
        onView(withText(R.string.AllPost)).check(matches(isDisplayed()));
        onView(withId(R.id.my_post_button)).check(matches(isDisplayed()));
        onView(withId(R.id.line3)).check(matches(isDisplayed()));

        onView(withId(R.id.paint_image)).check(matches(isDisplayed()));
        onView(withId(R.id.paint_button)).check(matches(isDisplayed()));
        onView(withText("Paint")).check(matches(isDisplayed()));
        onView(withId(R.id.paint)).check(matches(isDisplayed()));
        onView(withId(R.id.line4)).check(matches(isDisplayed()));

        onView(withId(R.id.logout_image)).check(matches(isDisplayed()));
        onView(withId(R.id.logout_button)).check(matches(isDisplayed()));
        onView(withText("Logout")).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
    }

    // This method test whether items is on correct position.
    @Test
    public void positionUITest() {
        onView(withId(R.id.username)).check(isCompletelyLeftOf(withId(R.id.roundedImageView2)));
        onView(withId(R.id.roundedImageView2)).check(isCompletelyAbove(withId(R.id.line1)));
        onView(withId(R.id.message_Image)).check(isCompletelyBelow(withId(R.id.line1)));

        onView(withId(R.id.message)).check(isCompletelyRightOf(withId(R.id.message_Image)));
        onView(withId(R.id.message)).check(isCompletelyLeftOf(withId(R.id.message_Button)));
        onView(withId(R.id.message_Button)).check(isCompletelyAbove(withId(R.id.line2)));

        onView(withId(R.id.line2)).check(isCompletelyAbove(withId(R.id.all_post_image)));
        onView(withId(R.id.all_post_image)).check(isCompletelyLeftOf(withId(R.id.my_post)));
        onView(withId(R.id.my_post)).check(isCompletelyLeftOf(withId(R.id.my_post_button)));
        onView(withId(R.id.line3)).check(isCompletelyBelow(withId(R.id.my_post_button)));

        onView(withId(R.id.line3)).check(isCompletelyAbove(withId(R.id.paint_image)));
        onView(withId(R.id.paint_button)).check(isCompletelyRightOf(withId(R.id.paint_image)));
        onView(withText("Paint")).check(isCompletelyLeftOf(withId(R.id.paint)));
        onView(withId(R.id.paint)).check(isCompletelyAbove(withId(R.id.line4)));

        onView(withId(R.id.line4)).check(isCompletelyBelow(withText("Paint")));
        onView(withId(R.id.line4)).check(isCompletelyAbove(withText("Logout")));
        onView(withId(R.id.logout_image)).check(isCompletelyLeftOf(withId(R.id.logout_button)));
        onView(withText("Logout")).check(isCompletelyLeftOf(withId(R.id.logout)));
    }

    // This method test whether button is click or not.
    @Test
    public void buttonTest() {
        onView(withId(R.id.message_Button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.my_post_button)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.paint)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.logout)).perform(click()).check(matches(isClickable()));
    }

    //This method test the intent from MainActivity to AllPostPage.Activity.
    @Test
    public void verifyToAllPostPage() {
        // Clicks a button
        onView(withId(R.id.my_post)).perform(click());

        // Verifies that the AllPostPage received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to AllPostPage.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the FAVORITEPOSTS is shown.
        onView(withText("FAVORITEPOSTS")).check(matches(withText("FAVORITEPOSTS")));
    }

    //This method test the intent from MainActivity to Message.Activity.
    @Test
    public void verifyToMessage() {
        // Clicks a button
        onView(withId(R.id.message)).perform(click());

        // Verifies that the MessageActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to MessageActivity.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the User list is shown.
        onView(withText("User list")).check(matches(withText("User list")));
    }

    //This method test the intent from MainActivity to Paint.Activity.
    @Test
    public void verifyToPaint() {
        // Clicks a button
        onView(withId(R.id.paint_button)).perform(click());

        // Verifies that the PaintActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to PaintActivity.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the draw view is shown.
        onView(withId(R.id.draw_view)).check(matches(withId(R.id.draw_view)));
    }

}
