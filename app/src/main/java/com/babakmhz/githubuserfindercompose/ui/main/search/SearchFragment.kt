package com.babakmhz.githubuserfindercompose.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.babakmhz.githubuserfindercompose.ui.components.SearchBar
import com.babakmhz.githubuserfindercompose.ui.main.MainViewModel
import com.babakmhz.githubuserfindercompose.ui.theme.GithubUserFinderComposeTheme


@ExperimentalComposeUiApi
class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val searchQuery = viewModel.searchQuery
                val scaffoldState = rememberScaffoldState()

                GithubUserFinderComposeTheme() {
                    Scaffold(
                        topBar = {
                            SearchBar(
                                query = searchQuery,
                                onQueryChanged = viewModel::searchUsers,
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }) {
                    }
                }
            }
        }


    }
}