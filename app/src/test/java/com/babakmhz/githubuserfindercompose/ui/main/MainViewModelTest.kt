package com.babakmhz.githubuserfindercompose.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.utils.CoroutineTestRule
import com.babakmhz.utils.FakeObjects
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repositoryHelper: RepositoryHelper

    private val delayForNetworkResponse = 1000L

    private val emptySearchResultFromApi: List<User> by lazy {
        arrayListOf()
    }


    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule(coroutineDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private suspend fun generateFakeFlow(): StateFlow<String> {
        val values = arrayListOf("value1", "value2", "value3")
        val mutableFlow = MutableStateFlow("")
        for (value in values) {
            delay(delayForNetworkResponse / 2)
            mutableFlow.value = value
        }

        return mutableFlow
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repositoryHelper = spyk()
        viewModel = MainViewModel(repositoryHelper, coroutineDispatcher)

    }

    @Test
    fun `test search users with length of 0 value should not change searchLiveDataState`() =
        coroutineDispatcher.runBlockingTest {

            coEvery { repositoryHelper.searchUsers(ofType(), ofType()) } coAnswers {
                delay(delayForNetworkResponse)
                flowOf(emptySearchResultFromApi)
            }
            viewModel.searchUserLiveData.observeForever {

            }
            val initialLiveDataState = viewModel.searchUserLiveData.value

            viewModel.searchUsers(MutableStateFlow(""))
            advanceTimeBy(delayForNetworkResponse)
            assertNotNull(viewModel.searchUserLiveData.value)
            assertEquals(initialLiveDataState, viewModel.searchUserLiveData.value)
        }

    @Test
    fun `test search users with valid query length value should change loadingState==true and change searchLiveDataState to response`() =
        coroutineDispatcher.runBlockingTest {

            val userResponse = arrayListOf(FakeObjects.user, FakeObjects.user.copy(id = 1))
            coEvery { repositoryHelper.searchUsers(ofType(), ofType()) } coAnswers {
                delay(delayForNetworkResponse)
                flowOf(userResponse)
            }

            viewModel.searchUserLiveData.observeForever {}
            viewModel.loadingLiveData.observeForever {}

            viewModel.searchUsers(generateFakeFlow())

            assertNotNull(viewModel.searchUserLiveData.value)
            assertNotNull(viewModel.loadingLiveData.value)

            advanceTimeBy(SEARCH_DELAY)
            assertEquals(viewModel.loadingLiveData.value, true)
            // waiting for network Response in queryFlow that we previously had
            advanceTimeBy(delayForNetworkResponse)
            assertEquals(userResponse, viewModel.searchUserLiveData.value)
            assertEquals(viewModel.loadingLiveData.value, false)
        }

    @Test
    fun `test search users with valid query length value throws Exception should change loading state multiple times and errorState to Error`() =
        coroutineDispatcher.runBlockingTest {

            val userResponse = Throwable("something went wrong")

            coEvery { repositoryHelper.searchUsers(ofType(), ofType()) } coAnswers {
                delay(delayForNetworkResponse)
                throw userResponse
            }

            viewModel.errorLiveData.observeForever {}
            viewModel.loadingLiveData.observeForever { }

            viewModel.searchUsers(generateFakeFlow())
            // waiting for network Response and in queryFlow that we previously had

            assertNotNull(viewModel.loadingLiveData.value)
            advanceTimeBy(SEARCH_DELAY)
            assertEquals(viewModel.loadingLiveData.value, true)

            advanceTimeBy(delayForNetworkResponse)
            assertNotNull(viewModel.errorLiveData.value)

            assertTrue(viewModel.errorLiveData.value is Throwable)
            assertEquals(viewModel.loadingLiveData.value, false)

        }


}