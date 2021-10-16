package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class EachPostTest {
    @Rule
    public ActivityTestRule<eachpost> ActivityTestRule =
            new ActivityTestRule<>(eachpost.class);

    @Test
    public void allPostUITest() {
        onView(withId(R.id.P)).check(isAbove(withId(R.id.good)));
//        onView(withId(R.id.P)).check(isPartiallyAbove(withId(R.id.good)));
        onView(withId(R.id.P)).check(isCompletelyAbove(withId(R.id.good)));

//        onView(withId(R.id.share)).check(isAbove(withId(R.id.good)));
        onView(withId(R.id.share)).check(isPartiallyAbove(withId(R.id.good)));
//        onView(withId(R.id.share)).check(isCompletelyAbove(withId(R.id.good)));

//        onView(withId(R.id.share)).check(isAbove(withId(R.id.comment)));
//        onView(withId(R.id.share)).check(isPartiallyBelow(withId(R.id.comment)));
        onView(withId(R.id.share)).check(isCompletelyAbove(withId(R.id.comment)));

        onView(withId(R.id.good)).check(isLeftOf(withId(R.id.comment)));
//        onView(withId(R.id.good)).check(isPartiallyLeftOf(withId(R.id.comment)));
        onView(withId(R.id.good)).check(isCompletelyLeftOf(withId(R.id.comment)));

        onView(withId(R.id.comment)).check(isLeftOf(withId(R.id.share)));
//        onView(withId(R.id.comment)).check(isPartiallyLeftOf(withId(R.id.share)));
        onView(withId(R.id.comment)).check(isCompletelyLeftOf(withId(R.id.share)));

        onView(withId(R.id.P)).check(isAbove(withId(R.id.img_url)));
//        onView(withId(R.id.P)).check(isPartiallyAbove(withId(R.id.img_url)));
        onView(withId(R.id.P)).check(isCompletelyAbove(withId(R.id.img_url)));

        onView(withId(R.id.img_url)).check(isAbove(withId(R.id.posttext)));
//        onView(withId(R.id.img_url)).check(isPartiallyAbove(withId(R.id.posttext)));
        onView(withId(R.id.img_url)).check(isCompletelyAbove(withId(R.id.posttext)));

        onView(withId(R.id.posttext)).check(isAbove(withId(R.id.publishtime)));
//        onView(withId(R.id.img_url)).check(isPartiallyAbove(withId(R.id.posttext)));
        onView(withId(R.id.publishtime)).check(isCompletelyAbove(withId(R.id.good)));

        onView(withId(R.id.comment_count)).check(isRightOf(withId(R.id.comment)));
//        onView(withId(R.id.comment_count)).check(isPartiallyRightOf(withId(R.id.comment)));
        onView(withId(R.id.comment_count)).check(isCompletelyRightOf(withId(R.id.comment)));
        onView(withId(R.id.comment_count)).check(isBelow(withId(R.id.share)));
//        onView(withId(R.id.comment_count)).check(isPartiallyBelow(withId(R.id.share)));
        onView(withId(R.id.comment_count)).check(isCompletelyBelow(withId(R.id.share)));
//

//        onView(withId(R.id.good_count)).check(isRightOf(withId(R.id.good)));
//        onView(withId(R.id.good_count)).check(isPartiallyRightOf(withId(R.id.good)));
        onView(withId(R.id.good_count)).check(isCompletelyRightOf(withId(R.id.good)));
        onView(withId(R.id.good_count)).check(isCompletelyLeftOf(withId(R.id.comment)));
    }


    @Test
    public void allPost() {
        onView(withId(R.id.good)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.share)).perform(click()).check(matches(isClickable()));
        onView(withId(R.id.comment)).perform(click()).check(matches(isClickable()));
    }

}
