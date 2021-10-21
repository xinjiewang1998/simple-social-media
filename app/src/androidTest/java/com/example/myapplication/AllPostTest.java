package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyBelow;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.post.AllPostPage;

import org.junit.Rule;
import org.junit.Test;

public class AllPostTest {
    @Rule
    public ActivityTestRule<AllPostPage> ActivityTestRule =
            new ActivityTestRule<>(AllPostPage.class);

    @Test
    public void allPostUITest() {
//        onView(withId(R.id.SearchPost)).check(isAbove(withId(R.id.AllPostView)));
//        onView(withId(R.id.SearchPost)).check(isPartiallyAbove(withId(R.id.AllPostView)));
        onView(withId(R.id.SearchPost)).check(isPartiallyBelow(withId(R.id.AllPostView)));
//        onView(withId(R.id.AllPostView)).check(isPartiallyBelow(withId(R.id.SearchPost)));
        onView(withId(R.id.AllPostView)).check(isPartiallyAbove(withId(R.id.SearchPost)));
    }


    @Test
    public void allPost() {
//        onView(withId(R.id.SearchPost)).perform(
//                typeText("search"), closeSoftKeyboard());
//        onView(withId(R.id.SearchPost)).perform(click());
//        onView(withId(R.id.SearchPost)).perform(pressBack());
        onView(withId(R.id.AllPostView)).perform(click());
//        onView(withId(R.id.AllPostView)).perform(pressBack());
    }

}
