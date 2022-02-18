package com.babakmhz.githubuserfindercompose.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.utils.launchWithException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val SEARCH_DELAY = 2000L

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
    val searchUserLiveData: LiveData<List<User>> = _searchUsersLiveData

    private var _userDetailsLiveData = MutableLiveData<User>()
    val userDetailsLiveData: LiveData<User> = _userDetailsLiveData

    private var page = 0

    fun searchUsers(queryFlow: StateFlow<String>) =
        viewModelScope.launchWithException(_errorState, _loadingState) {

            queryFlow.filter { username ->
                //for avoiding unwanted network calls and handling rate limits
                return@filter username.isNotEmpty()
            }
                .distinctUntilChanged()
                .debounce(SEARCH_DELAY) // making sure user is complete with typing
                .flatMapLatest {
                    // getting result of last input with page 0 as it's a new input change
                    _loadingState.postValue(true)
                    page = 0
                    repositoryHelper.searchUsers(it, page)
                }
                .flowOn(flowDispatcher)
                .catch { e ->
                    _loadingState.postValue(false)
                    _errorState.postValue(e)
                }
                .collect {
                    // emitting data
                    _loadingState.postValue(false)
                    _searchUsersLiveData.postValue(it)
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