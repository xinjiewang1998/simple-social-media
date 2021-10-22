package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isPartiallyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.myapplication.chat.ChatBoxActivity;

import org.junit.Rule;
import org.junit.Test;

public class ChatBoxTest {
    /* Must execute after the StartActivityLoginTest*/

    //This class is for testing the ChatBox.
    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<ChatBoxActivity> mIntentsRule =
            new IntentsTestRule<>(ChatBoxActivity.class);

    // Check the display on screen.
    @Test
    public void displayUITest() {
        onView(withId(R.id.friend_name)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_button)).check(matches(isDisplayed()));
        onView(withId(R.id.pic)).check(matches(isDisplayed()));
        onView(withId(R.id.send)).check(matches(isDisplayed()));
        onView(withText("Send")).check(matches(isDisplayed()));
        onView(withId(R.id.chat_box)).check(matches(isDisplayed()));
        onView(withId(R.id.input_text)).check(matches(isDisplayed()));
    }

    // Check the position of items.
    @Test
    public void positionUITest() {
        onView(withId(R.id.friend_name)).check(isPartiallyRightOf(withId(R.id.pic)));
        onView(withId(R.id.friend_name)).check(isPartiallyLeftOf(withId(R.id.profile_button)));
        onView(withId(R.id.friend_name)).check(isCompletelyAbove(withId(R.id.chat_box)));

        onView(withId(R.id.chat_box)).check(isCompletelyBelow(withId(R.id.pic)));
        onView(withId(R.id.chat_box)).check(isCompletelyAbove(withId(R.id.input_text)));
        onView(withId(R.id.input_text)).check(isCompletelyLeftOf(withId(R.id.send)));
    }

    @Test
    public void sendTest(){
        onView(withId(R.id.input_text))
                .perform(typeText("send a message"), closeSoftKeyboard());
        onView(withId(R.id.send)).perform(click());
    }
}
