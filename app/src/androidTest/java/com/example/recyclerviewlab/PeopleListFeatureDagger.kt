package com.example.recyclerviewlab

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
class PeopleListFeatureDagger : BaseUiTest() {

    private val app: BaseApplication = InstrumentationRegistry.getInstrumentation()
        .targetContext.applicationContext as BaseApplication

    @get:Rule
    val activityRule = object: ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            app.setTestComponent(DaggerTestAppComponent.factory().create(app))
        }
    }


    @Test
    fun displaysListOfPlayLists() {
        assertRecyclerViewItemCount(R.id.peopleLists_list, 3*3)
    }
}