package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.myapplication.post.EachPostActivity;
import com.example.myapplication.post.FavoritePostActivity;

import org.junit.Rule;
import org.junit.Test;

public class EachPostTest {

    /* Must execute after the StartActivityLoginTest*/

    //This class is for testing the EachPost.
    @Rule
    public IntentsTestRule<EachPostActivity> mIntentsRule =
            new IntentsTestRule<>(EachPostActivity.class);

    // This method test whether items is display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.P)).check(matches(isDisplayed()));
        onView(withText(R.string.P)).check(matches(isDisplayed()));
        onView(withId(R.id.eachPostImage)).check(matches(isDisplayed()));
        onView(withId(R.id.Like)).check(matches(isDisplayed()));
        onView(withText(R.string.good)).check(matches(isDisplayed()));
        onView(withId(R.id.Favorite)).check(matches(isDisplayed()));
    }

    // This method test whether items is on correct position.
    @Test
    public void positionUITest() {
        onView(withId(R.id.P)).check(isCompletelyAbove(withId(R.id.eachPostImage)));
        onView(withId(R.id.eachPostImage)).check(isCompletelyBelow(withText(R.string.P)));
        onView(withText(R.string.P)).check(isCompletelyRightOf(withId(R.id.Like)));

        onView(withId(R.id.Like)).check(isCompletelyBelow(withId(R.id.eachPostImage)));
        onView(withId(R.id.Favorite)).check(isCompletelyRightOf(withText(R.string.good)));
    }

    // This method test whether button is click or not.
    @Test
    public void buttonTest() {
        onView(withId(R.id.Like)).check(matches(isClickable()));
        onView(withText(R.string.good)).check(matches(isClickable()));
        onView(withId(R.id.Favorite)).check(matches(isClickable()));
    }

}
