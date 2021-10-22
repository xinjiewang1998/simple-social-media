package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class LogoutTest {

    /* Must execute after the StartActivityLoginTest*/

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule =
            new IntentsTestRule<>(MainActivity.class);

    //This method test for logout.
    @Test
    public void verifyLogout() {
        // Clicks a button
        onView(withId(R.id.logout_button)).perform(click());

        // Verifies that the StartActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to StartActivity.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the login page is shown.
        onView(withText("LOGIN")).check(matches(withText("LOGIN")));
    }
}
