package com.babakmhz.githubuserfindercompose.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.Constants.PAGE_SIZE
import com.babakmhz.githubuserfindercompose.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper,
    private val flowDispatcher: CoroutineDispatcher,
) : ViewModel() {

    // using mutableState is also possible instead of livedata in case of composable UIs

    private var _loadingState = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> = _loadingState

    private var _errorState: MutableLiveData<Throwable?> = MutableLiveData(null)
    val errorLiveData: LiveData<Throwable?> = _errorState

    private var _searchUsersLiveData = MutableLiveData<List<User>>(arrayListOf())
    val searchUsersLiveData: LiveData<List<User>> = _searchUsersLiveData

    private var _userDetailsLiveData = MutableLiveData<User>()
    val userDetailsLiveData: LiveData<User> = _userDetailsLiveData

    val searchQuery = mutableStateOf("")
    val page = mutableStateOf(1)

    private var usersListScrollPosition = 0

    val shouldScrollListToTop = mutableStateOf(false)

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
                    // getting result of last input with page 0 as it's a new input change
                    _loadingState.postValue(true)
                    page.value = 1
                    repositoryHelper.searchUsers(it, page.value)
                }.catch { e ->
                    _loadingState.postValue(false)
                    _errorState.postValue(e)
                }.flowOn(flowDispatcher)

                .collect {
                    // emitting data
                    _loadingState.postValue(false)
                    _searchUsersLiveData.postValue(it)
                    setShouldScrollListToTopState(true)
                }
        }

    fun onChangeRecipeScrollPosition(position: Int) {
        usersListScrollPosition = position
    }

    private fun setShouldScrollListToTopState(scrollToTop: Boolean) {
        shouldScrollListToTop.value = scrollToTop
        onChangeRecipeScrollPosition(if (scrollToTop) 0 else usersListScrollPosition)
    }

    fun getQueryNextPage() {
        if ((usersListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            viewModelScope.launch {
                _loadingState.postValue(true)
                page.value++
                repositoryHelper.searchUsers(searchQuery.value, page.value)
                    .catch { e ->
                        _errorState.postValue(e)
                        _loadingState.postValue(false)
                    }.collect {
                        val previousResult = searchUsersLiveData.value
                        val result = ArrayList<User>()
                        result.addAll(previousResult!!.toList())
                        result.addAll(it.toList())
                        _searchUsersLiveData.postValue(result)
                        _loadingState.postValue(false)
                        setShouldScrollListToTopState(false)

                    }
            }
        }

    }

    fun getUserDetails(username: String) =
        viewModelScope.launchWithException(_errorState, _loadingState) {
            _loadingState.postValue(true)
            val response = repositoryHelper.getUserDetails(username)
            _loadingState.postValue(false)
            _userDetailsLiveData.postValue(response)
        }
}

const val SEARCH_DELAY = 3000L

