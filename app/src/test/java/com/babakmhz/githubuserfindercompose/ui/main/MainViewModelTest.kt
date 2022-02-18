package com.babakmhz.githubuserfindercompose.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.utils.CoroutineTestRule
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

    private val emptySearchResultFromApi:List<User> by lazy {
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
            mutableFlow.value = value
        }

        return mutableFlow
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repositoryHelper = spyk()
        viewModel = MainViewModel(repositoryHelper)

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


}