package com.example.diakabana;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login), withText("Connectez vous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }
    /** Testing for Missing Uppercase letter*/
    @Test
    public void testFindMissingUpperCase(){
        // Find the password EditText and type in the weak password
        onView(withId(R.id.editTextTextPassword))
                .perform(replaceText("password123#$*"), closeSoftKeyboard());

        // Find the login button and click it
        onView(withId(R.id.login))
                .perform(click());

        // Find the TextView and check that it shows the expected error message
        onView(withId(R.id.textView))
                .check(matches(withText("You shall not pass!")));
    }
    /** Test for missing lowercase letter*/
    @Test
    public void testFindMissingLowerCase(){
        // Find the password EditText and type in the weak password
        onView(withId(R.id.editTextTextPassword))
                .perform(replaceText("PASS123#$*"), closeSoftKeyboard());

        // Find the login button and click it
        onView(withId(R.id.login))
                .perform(click());

        // Find the TextView and check that it shows the expected error message
        onView(withId(R.id.textView))
                .check(matches(withText("You shall not pass!")));
    }
    /** Test for missing digits*/
    @Test
    public void testFindMissingdigit(){
        // Find the password EditText and type in the weak password
        onView(withId(R.id.editTextTextPassword))
                .perform(replaceText("Pass#$*"), closeSoftKeyboard());

        // Find the login button and click it
        onView(withId(R.id.login))
                .perform(click());

        // Find the TextView and check that it shows the expected error message
        onView(withId(R.id.textView))
                .check(matches(withText("You shall not pass!")));
    }
    /** Test for missing special character*/
    @Test
    public void testFindMissingSpecialCharacter(){
        // Find the password EditText and type in the weak password
        onView(withId(R.id.editTextTextPassword))
                .perform(replaceText("Pass123"), closeSoftKeyboard());

        // Find the login button and click it
        onView(withId(R.id.login))
                .perform(click());

        // Find the TextView and check that it shows the expected error message
        onView(withId(R.id.textView))
                .check(matches(withText("You shall not pass!")));
    }
    /** Test with a password complex enough*/
    @Test
    public void testForGoodPass(){
        // Find the password EditText and type in the weak password
        onView(withId(R.id.editTextTextPassword))
                .perform(replaceText("Pass123#$*"), closeSoftKeyboard());

        // Find the login button and click it
        onView(withId(R.id.login))
                .perform(click());

        // Find the TextView and check that it shows the expected error message
        onView(withId(R.id.textView))
                .check(matches(withText("Your password is complex enough")));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
