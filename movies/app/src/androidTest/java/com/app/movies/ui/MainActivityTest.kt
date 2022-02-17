package com.app.movies.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.app.movies.R
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testSearchViewisDisplayed() {
        onView(withId(R.id.search_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_text)).perform(clearText(), typeText("new"))
        onView(withId(R.id.recyclerView)).perform(closeSoftKeyboard())

    }

}