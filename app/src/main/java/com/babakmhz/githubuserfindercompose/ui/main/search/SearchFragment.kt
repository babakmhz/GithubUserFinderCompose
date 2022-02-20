package com.babakmhz.githubuserfindercompose.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.babakmhz.githubuserfindercompose.R
import com.babakmhz.githubuserfindercompose.ui.components.SearchBar
import com.babakmhz.githubuserfindercompose.ui.components.UserList
import com.babakmhz.githubuserfindercompose.ui.components.util.SnackbarController
import com.babakmhz.githubuserfindercompose.ui.main.MainViewModel
import com.babakmhz.githubuserfindercompose.ui.theme.GithubUserFinderComposeTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@FlowPreview
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private val snackbarController = SnackbarController(lifecycleScope)


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
                                },
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }) {

                        Box(
                            contentAlignment = Alignment.Center,
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

                                },
                            )
                            if (loading == true) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .align(if (usersList.isNullOrEmpty()) Alignment.Center else Alignment.BottomEnd)

                                )
                            }

                            error?.let {
                                if (usersList.isNullOrEmpty()) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.error_fetching_data_message),
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Button(onClick = {

                                        }) {
                                            Text(
                                                text = "Retry",
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                } else {
                                    // show snackBar
                                    val errorMessage =
                                        stringResource(id = R.string.error_fetching_data_message)
                                    snackbarController.getScope().launch {
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = errorMessage,
                                            actionLabel = ""
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }


    }


}



