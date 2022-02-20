package com.babakmhz.githubuserfindercompose.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.babakmhz.githubuserfindercompose.R
import com.babakmhz.githubuserfindercompose.ui.components.ConnectionErrorWithRetry
import com.babakmhz.githubuserfindercompose.ui.components.LoadingIndicator
import com.babakmhz.githubuserfindercompose.ui.components.SearchBar
import com.babakmhz.githubuserfindercompose.ui.components.UserList
import com.babakmhz.githubuserfindercompose.ui.main.MainViewModel
import com.babakmhz.githubuserfindercompose.ui.theme.GithubUserFinderComposeTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow


@FlowPreview
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val searchQuery = viewModel.searchQuery
                val scaffoldState = rememberScaffoldState()
                val loading by viewModel.loadingLiveData.observeAsState()
                val usersList by viewModel.searchUsersLiveData.observeAsState()
                val error by viewModel.errorLiveData.observeAsState()
                val page = viewModel.page.value
                val searchStateFlow = MutableStateFlow("")
                viewModel.registerSearchFlow(searchStateFlow)
                GithubUserFinderComposeTheme {
                    Scaffold(
                        topBar = {
                            SearchBar(
                                query = searchQuery,
                                onQueryChanged = {
                                    searchStateFlow.value = it
                                    viewModel.checkForEmptyQuery(it)
                                },
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }) {

                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {

                            UserList(
                                loading = loading!!,
                                users = usersList!!,
                                onChangeScrollPosition = {
                                    viewModel.onChangeSearchListScrollPosition(it)
                                },
                                page = page,
                                onTriggerNextPage = {
                                    viewModel.getQueryNextPage()
                                },
                                onNavigateToDetailScreen = {
                                    val action =
                                        SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                                            it
                                        )
                                    findNavController().navigate(action)
                                },
                            )
                            if (loading == true) {
                                LoadingIndicator(
                                    modifier =
                                    Modifier
                                        .padding(24.dp)
                                        .align(if (usersList.isNullOrEmpty()) Alignment.Center else Alignment.BottomEnd)
                                )
                            }

                            if (usersList!!.isEmpty() && loading == false && error == null) {
                                Text(
                                    text = stringResource(R.string.nothing_here),
                                    Modifier.align(Alignment.Center)
                                )
                            }

                            error?.let {
                                if (usersList.isNullOrEmpty())
                                    ConnectionErrorWithRetry(
                                        modifier = Modifier.align(Alignment.Center),
                                        onRetryClicked = {
                                            viewModel.retrySearch(searchQuery.value)
                                        })
                            }

                        }
                    }
                }
            }
        }


    }


}




