package com.babakmhz.githubuserfindercompose.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.Constants.PAGE_SIZE_CONFIG_FOR_API
import com.babakmhz.githubuserfindercompose.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper,
    private val flowDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var _loadingState = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> = _loadingState

    private var _errorState: MutableLiveData<Throwable?> = MutableLiveData(null)
    val errorLiveData: LiveData<Throwable?> = _errorState

    private var _searchUsersLiveData = MutableLiveData<List<User>>(arrayListOf())
    val searchUsersLiveData: LiveData<List<User>> = _searchUsersLiveData

    private var _userDetailsLiveData = MutableLiveData<User?>()
    val userDetailsLiveData: LiveData<User?> = _userDetailsLiveData

    val searchQuery = mutableStateOf("")
    val page = mutableStateOf(1)

    private var usersListScrollPosition = 0

    private var retryDelay = 3000L
    private val retryDelayFactor = 2

    @FlowPreview
    fun registerSearchFlow(queryFlow: StateFlow<String>) =
        viewModelScope.launch(flowDispatcher) {
            queryFlow.debounce(SEARCH_DELAY)
                .filter { username ->
                    //for avoiding unwanted network calls and handling rate limits

                    return@filter username.isNotEmpty()
                }
                // making sure user is complete with typing
                .flatMapLatest {
                    // getting result of last input with page 1 as it's a new input change
                    _loadingState.postValue(true)
                    prepareForNewSearch()
                    repositoryHelper.searchUsers(it, page = 1)
                        .catch { e ->
                            _loadingState.postValue(false)
                            _errorState.postValue(e)
                        }
                }.flowOn(flowDispatcher)

                .collect {
                    // emitting data
                    _loadingState.postValue(false)
                    _searchUsersLiveData.postValue(it)
                }
        }

    fun onChangeSearchListScrollPosition(position: Int) {
        usersListScrollPosition = position
    }

    fun checkForEmptyQuery(query: String){
        // making search list empty when the query text is empty
        // as the instant search works
        if (query.isEmpty())
            _searchUsersLiveData.postValue(arrayListOf())
    }

    private fun prepareForNewSearch() {
        _searchUsersLiveData.postValue(listOf())
        _errorState.postValue(null)
        page.value = 1
        onChangeSearchListScrollPosition(0)
    }

    fun getQueryNextPage() {
        // handling pagination in manual way
        if ((usersListScrollPosition + 1) >= (page.value * PAGE_SIZE_CONFIG_FOR_API)) {

            viewModelScope.launch {
                _loadingState.postValue(true)

                // increasing the page to avoid multiple calls
                page.value++

                repositoryHelper.searchUsers(searchQuery.value, page.value)
                    .retry(retries = 3) {

                        delay(retryDelay)
                        retryDelay = (retryDelay * retryDelayFactor)
                        return@retry true

                    }.catch { e ->

                        // if error happened in getting the next page
                        _errorState.postValue(e)
                        _loadingState.postValue(false)
                        // decrementing the page number to make function callable again for the next page
                        page.value--

                    }.collect {
                        // appending the next page result to the previous result
                        val previousResult = searchUsersLiveData.value
                        val result = ArrayList<User>()
                        result.addAll(previousResult!!.toList())
                        result.addAll(it.toList())
                        _searchUsersLiveData.postValue(result)
                        _loadingState.postValue(false)
                    }
            }
        }

    }

    fun getUserDetails(username: String) =
        viewModelScope.launchWithException(_errorState, _loadingState) {
            _loadingState.postValue(true)
            val response = repositoryHelper.getUserDetails(username)
            _userDetailsLiveData.postValue(response)
            _loadingState.postValue(false)
        }


    fun retrySearch(query: String) = viewModelScope.launch {
        // resetting the states
        prepareForNewSearch()
        _loadingState.postValue(true)
        repositoryHelper.searchUsers(query, page = 1)
            .catch { e ->
                // if error happened
                _loadingState.postValue(false)
                _errorState.postValue(e)
            }
            .flowOn(flowDispatcher)
            .collect {
                // emitting data
                _loadingState.postValue(false)
                _searchUsersLiveData.postValue(it)
            }
    }


    fun onDetailsFragmentDestroyed()  {
        _userDetailsLiveData.postValue(null)
        _errorState.postValue(null)
    }
}

const val SEARCH_DELAY = 1500L

