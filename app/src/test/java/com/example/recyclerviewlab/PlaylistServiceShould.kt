package com.example.recyclerviewlab

import com.example.recyclerviewlab.backend.Backend
import com.example.recyclerviewlab.backend.sendAwait
import com.example.recyclerviewlab.sectionList.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PeopleListServiceShould : BaseUnitTest() {

    private val api: Backend = mock()
    private lateinit var service: PeopleListService
    private val peoplesList: PeopleResult = mock()

    @Test
    fun fetchPlaylistsFromApi() = runBlockingTest {
        service = PeopleListService(api)
        service.fetchPeopleLists().first()
        verify(api, times(1)).sendAwait()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(Result.success(peoplesList), service.fetchPeopleLists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()
        assertEquals(
            "Something went wrong",
            service.fetchPeopleLists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.sendAwait()).thenThrow(RuntimeException("Damn backend developers"))

        service = PeopleListService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.sendAwait()).thenReturn(peoplesList)
        service = PeopleListService(api)
    }
}