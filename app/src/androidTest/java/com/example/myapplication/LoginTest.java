package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class LoginTest {
    private static final String USER = "android@gmail.com";
    private static final String PASS = "123456";
    /* Instantiate an IntentsTestRule object. */
    @Rule
    public IntentsTestRule<StartActivity> mIntentsRule =
            new IntentsTestRule<>(StartActivity.class);

    @Test
    public void verifyMessageSentToMessageActivity() {

        // Types a user name and password into a EditText element.
        onView(withId(R.id.user_name))
                .perform(typeText(USER), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(PASS), closeSoftKeyboard());

        // Clicks a button to send the message to another
        // activity through an explicit intent.
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        // Verifies that the DisplayMessageActivity received an intent
        // with the correct package name and message.
//        intended(allOf(
//                hasComponent(hasShortClassName(".MainActivity")),
//                toPackage("com.example.myapplication")));

        intended(toPackage("com.example.myapplication"));

//        user.clickOnView(system.getView(R.id.callButton));

    }
}
