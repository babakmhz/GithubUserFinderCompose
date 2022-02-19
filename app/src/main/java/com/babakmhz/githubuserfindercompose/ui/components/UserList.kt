package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.Constants.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun UserList(
    loading: Boolean,
    users: List<User>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToDetailScreen: (Int) -> Unit,
    scrollToTop: Boolean = true
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {

        LazyColumn {
            itemsIndexed(
                items = users
            ) { index, user ->
                onChangeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    onTriggerNextPage()
                }

                UserItem(
                    user = user,
                    onClick = {
                        onNavigateToDetailScreen(user.id)
                    }
                )
            }
        }



    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Preview
@Composable
fun previewUserList() {
    UserList(
        users = arrayListOf(fakeUser, fakeUser, fakeUser),
        onChangeScrollPosition = {},
        page = 1,
        onTriggerNextPage = { /*TODO*/ },
        onNavigateToDetailScreen = {},
        loading = false
    )
}




