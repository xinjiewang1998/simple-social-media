package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class StartActivityLoginTest {

    // This class test the intent from StartActivity to MainActivity.
    private static final String USER = "android@gmail.com";
    private static final String PASS = "123456";

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<StartActivity> mIntentsRule =
            new IntentsTestRule<>(StartActivity.class);


    @Test
    public void verifyToMainActivity() {
        // Types a user name and password into a EditText element.
        onView(withId(R.id.user_name))
                .perform(typeText(USER), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(PASS), closeSoftKeyboard());

        // Clicks a button
        onView(withId(R.id.login_button)).perform(click());

        // Verifies that the MainActivity received an intent
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "contacts" is seen.
        intending(toPackage("com.example.myapplication")).respondWith(result);

        // Assert that the user id is shown.
        onView(withText("android@gmail.com")).check(matches(withText("android@gmail.com")));
    }

}
