package com.example.recyclerviewlab

import com.example.recyclerviewlab.sectionList.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PeopleListRepositoryShould : BaseUnitTest() {

    private val service: PeopleListService = mock()
    private val peopleLists = mock<List<People>>()
    private val peopleListsRaw = mock<PeopleResult>()
    private val mapper: PeopleListMapper = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest{
        val repository = mockSuccessfulCase()
        repository.getPeopleLists()
        verify(service, times(1)).fetchPeopleLists()
    }

    @Test
    fun emitPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(peopleLists, repository.getPeopleLists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPeopleLists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPeopleLists().first()
        verify(mapper, times(1)).invoke(peopleListsRaw)
    }

    private suspend fun mockFailureCase(): PeopleListRepository {
        whenever(service.fetchPeopleLists()).thenReturn(
            flow {
                emit(Result.failure<PeopleResult>(exception))
            }
        )
        return PeopleListRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PeopleListRepository {
        whenever(service.fetchPeopleLists()).thenReturn(
            flow {
                emit(Result.success(peopleListsRaw))
            }
        )
        whenever(mapper.invoke(peopleListsRaw)).thenReturn(peopleLists)
        return PeopleListRepository(service, mapper)
    }
}