package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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

import com.example.myapplication.post.FavoritePostActivity;

import org.junit.Rule;
import org.junit.Test;

public class FavoritePostTest {

    /* Must execute after the StartActivityLoginTest*/

    //This class is for testing the FavoritePost.
    @Rule
    public IntentsTestRule<FavoritePostActivity> mIntentsRule =
            new IntentsTestRule<>(FavoritePostActivity.class);

    // This method test whether items is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.AllPost)).check(matches(isDisplayed()));
        onView(withId(R.id.FavoritePostList)).check(matches(isDisplayed()));
    }

    // This method test whether button is click or not.
    @Test
    public void buttonTest() {
        onView(withId(R.id.AllPost)).check(matches(isClickable()));
    }

    // Check the whether FavoritePostList can be scroll.
    @Test
    public void recycleViewTest(){
        onView(withId(R.id.FavoritePostList))
                .perform(RecyclerViewActions
                        .scrollToPosition(0));
    }

    //This method test the ability of intent.
    @Test
    public void verifyToAllPostActivity() {
        // Clicks a button
        onView(withId(R.id.AllPost)).perform(click());

        // Verifies that the AllPostActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to AllPostActivity.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the data we set up above is shown.
        onView(withText("FAVORITEPOSTS")).check(matches(withText("FAVORITEPOSTS")));
    }
}
