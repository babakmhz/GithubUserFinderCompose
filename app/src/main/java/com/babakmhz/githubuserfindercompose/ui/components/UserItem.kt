package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
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
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke(user) }),
        elevation = 8.dp,
    ) {

        Column() {
            Row() {

                Image(
                    painter = rememberImagePainter(
                        data = user.avatar_url,
                        builder = {
                            placeholder(R.drawable.ic_launcher_background)
                            crossfade(true)
                            crossfade(600)
                            scale(Scale.FILL)
                            transformations(CircleCropTransformation())

                        }
                    ),

                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp,
                            start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = user.username,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h4
                    )
                    Text(
                        text = stringResource(id = R.string.id_d, user.id),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5
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

