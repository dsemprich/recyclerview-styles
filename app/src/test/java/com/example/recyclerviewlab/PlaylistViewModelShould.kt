package com.example.recyclerviewlab

import com.example.recyclerviewlab.sectionList.People
import com.example.recyclerviewlab.sectionList.PeopleListRepository
import com.example.recyclerviewlab.sectionList.PeopleListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PeopleListViewModelShould: BaseUnitTest() {

    private val repository: PeopleListRepository = mock()
    private val peopleLists = mock<List<People>>()
    private val expected = Result.success(peopleLists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPeopleListsFromRepository() = runBlockingTest {

        val viewModel = mockSuccessfulCase()

        viewModel.peoples.getValueForTest()

        verify(repository, times(1)).getPeopleLists()
    }

    @Test
    fun emitsPeopleListsFromRepository() = runBlockingTest {

        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.peoples.getValueForTest())

    }

    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockErrorCase()

        assertEquals(
            exception,
            viewModel.peoples.getValueForTest()!!.exceptionOrNull()
        )
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.peoples.getValueForTest()

            assertEquals(true, values.first())
        }
    }

    @Test
    fun closeLoaderAfterPeopleListsLoaded() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.peoples.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.peoples.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): PeopleListViewModel {
        runBlockingTest {
            whenever(repository.getPeopleLists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PeopleListViewModel(repository)
    }

    private fun mockErrorCase(): PeopleListViewModel {
        runBlockingTest {
            whenever(repository.getPeopleLists()).thenReturn(
                flow {
                    emit(Result.failure<List<People>>(exception))
                }
            )
        }
        return PeopleListViewModel(repository)
    }
}