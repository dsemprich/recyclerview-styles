package com.example.recyclerviewlab

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.recyclerviewlab.sectionList.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed

import org.junit.Test
import org.junit.runner.RunWith

import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class PeopleListFeature : BaseUiTest() {

    val activityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displaysListOfPlayLists() {

        assertRecyclerViewItemCount(R.id.peopleLists_list, 10)

        onView(allOf(withId(R.id.first_name), isDescendantOfA(nthChildOf(withId(R.id.peopleLists_list), 0))))
            .check(matches(withText("Esperanza")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.last_name), isDescendantOfA(nthChildOf(withId(R.id.peopleLists_list), 0))))
            .check(matches(withText("Ortega")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylist() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {
        assertNotDisplayed(R.id.loader)
    }
}