package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babakmhz.githubuserfindercompose.R
import com.babakmhz.githubuserfindercompose.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun UserItem(
    user: User,
    onClick: (User) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 2.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke(user) }),
        elevation = 8.dp,
    ) {

        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(4.dp)
            ) {

                CircularImage(imageUrl = user.avatar_url)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp, bottom = 12.dp,
                            start = 8.dp, end = 8.dp
                        )
                ) {
                    Text(
                        text = user.username,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5
                    )

                    user.score?.let {
                        Text(
                            text = stringResource(id = R.string.score_s, it.toString()),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.Start),
                            fontSize = 15.sp

                        )
                    }
                    Text(
                        text = stringResource(id = R.string.id_d, user.id),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Preview
@Composable
fun previewUserItem() {
    UserItem(
        user = fakeUser
    ) {

    }
}

val fakeUser = User(
    avatar_url = "https://avatars.githubusercontent.com/u/31562825?v=4",
    events_url = "",
    followers_url = "",
    following_url = "",
    gists_url = "",
    gravatar_id = "",
    html_url = "https://github.com/babakmhz",
    id = 10,
    username = "babakmhz",
    node_id = "",
    organizations_url = "",
    received_events_url = "",
    repos_url = "",
    score = null,
    site_admin = false,
    starred_url = "",
    subscriptions_url = "",
    type = "",
    url = ""
)

