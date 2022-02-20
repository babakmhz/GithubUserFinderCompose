package com.babakmhz.githubuserfindercompose.ui.main.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.ui.components.CircularImage
import com.babakmhz.githubuserfindercompose.ui.components.LoadingIndicator
import com.babakmhz.githubuserfindercompose.ui.components.fakeUser
import com.babakmhz.githubuserfindercompose.ui.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val loading by viewModel.loadingLiveData.observeAsState()
                val user by viewModel.userDetailsLiveData.observeAsState()
                if (loading == true) {
                    LoadingIndicator()
                } else
                    user?.let {
                        DetailsScreen(user = it, onOpenProfileClicked = {
                            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                        })
                    }
            }
        }


}

@Composable
fun DetailsScreen(user: User, onOpenProfileClicked: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CircularImage(imageUrl = fakeUser.avatar_url)
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.align(Alignment.Top)) {
            Text(text = "Username: ${user.username}")
            Text(text = "Bio: ${user.userDetails?.bio}")
            Text(text = "Public Repos: ${user.userDetails?.public_repos}")
            Text(text = "Score: ${user.score}")
            ClickableText(text =
            AnnotatedString("Open Full Profile", SpanStyle(Color.Blue)), onClick = {
                onOpenProfileClicked.invoke(user.html_url)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewDetailsScreen() {
    DetailsScreen(user = fakeUser, {
    })
}
