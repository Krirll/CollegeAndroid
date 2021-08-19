package com.example.practicandroid7_8

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class InstrumentedTestResultOfEquation {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.practicandroid7_8", appContext.packageName)
    }

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun result_isCorrect1() {
        onView(withId(R.id.firstNumber)).perform(typeText("2"))
        onView(withId(R.id.secondNumber)).perform(typeText("3"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("x < -1.500")))
    }

    @Test
    fun result_isCorrect2() {
        onView(withId(R.id.firstNumber)).perform(typeText("0"))
        onView(withId(R.id.secondNumber)).perform(typeText("30.125"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText(R.string.result2)))
    }

    @Test
    fun result_isCorrect3() {
        onView(withId(R.id.firstNumber)).perform(typeText("0"))
        onView(withId(R.id.secondNumber)).perform(typeText("-3"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText(R.string.result1)))
    }

    @Test
    fun result_isCorrect4() {
        onView(withId(R.id.firstNumber)).perform(typeText("1"))
        onView(withId(R.id.secondNumber)).perform(typeText("some"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText(R.string.error)))
    }
}