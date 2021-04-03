package com.example.recyclerviewlab

import android.content.Context
import android.content.res.Resources
import com.example.recyclerviewlab.mockData.PeopleMockData
import com.example.recyclerviewlab.sectionList.People
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

import org.junit.Assert.*
import java.io.File

class MockDataShouldLoad {

    private val mockResources: Resources = mock {
        on { openRawResource(eq(R.raw.faker_api_female_2021)) } doReturn(File("src/test/resources/faker_api_2021.json").inputStream())
    }
    private val mockContext: Context = mock() {
        on { resources } doReturn(mockResources)
    }


    @Test
    fun loadPeopleMock() {
        val mock = PeopleMockData.peopleMockResponse(mockContext)
        assertNotNull(mock)
    }

    @Test
    fun loadJsonToPeopleModel() {
        val mock = PeopleMockData.peopleMockResponse(mockContext)
        val people: List<People> = Gson().fromJson(mock, Array<People>::class.java).toList()
        assertNotNull(people)
        val found = people.find { it.firstName == "Stefan" }
        assertEquals("Stefan", found?.firstName)
    }
}