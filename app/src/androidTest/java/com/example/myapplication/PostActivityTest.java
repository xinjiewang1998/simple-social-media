package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
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

import org.junit.Rule;
import org.junit.Test;

public class PostActivityTest {

    /* Must execute after the StartActivityLoginTest*/

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<PostActivity> mIntentsRule =
            new IntentsTestRule<>(PostActivity.class);

    // Check the SearchPost, FavoritePost and AllPostView is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.SearchPost)).check(matches(isDisplayed()));
        onView(withId(R.id.FavoritePost)).check(matches(isDisplayed()));
        onView(withId(R.id.AllPostView)).check(matches(isDisplayed()));
    }

    // Check the position of SearchPost, FavoritePost and AllPostView.
    @Test
    public void positionUITest() {
        onView(withId(R.id.SearchPost)).check(isCompletelyAbove(withId(R.id.FavoritePost)));
        onView(withText(R.string.FavoritePost)).check(isCompletelyBelow(withId(R.id.SearchPost)));
        onView(withId(R.id.FavoritePost)).check(isCompletelyAbove(withId(R.id.AllPostView)));
    }

    // Check the button "FavoritePost" is clickable.
    @Test
    public void buttonTest() {
        onView(withId(R.id.FavoritePost)).check(matches(isClickable()));
    }

    // Check the whether AllPostView can be scroll.
    @Test
    public void recycleViewTest(){
        onView(withId(R.id.AllPostView))
                .perform(RecyclerViewActions
                        .scrollToPosition(0));
    }

    // Check the searchView through type some words.
    @Test
    public void searchViewTest(){
        onView(withId(R.id.SearchPost)).perform(click());
        onView(withId(R.id.SearchPost)).perform(typeText("#WHPmatching"));
    }

    //This method test the ability of intent.
    @Test
    public void verifyToFavoritePostActivity() {
        // Clicks a button
        onView(withId(R.id.FavoritePost)).perform(click());

        // Verifies that the FavoritePostActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to FavoritePostActivity.
        intending(toPackage("com.example.myapplication.post")).respondWith(result);

        // Assert that the data we set up above is shown.
        onView(withText("POST")).check(matches(isDisplayed()));
    }

}
