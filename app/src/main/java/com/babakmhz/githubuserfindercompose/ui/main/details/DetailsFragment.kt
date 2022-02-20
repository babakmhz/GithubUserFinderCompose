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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.babakmhz.githubuserfindercompose.R
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.ui.components.CircularImage
import com.babakmhz.githubuserfindercompose.ui.components.fakeUser
import com.babakmhz.githubuserfindercompose.ui.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserDetails(args.username)
    }

    override fun getTheme(): Int {
        return R.style.BaseBottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val user by viewModel.userDetailsLiveData.observeAsState()
                val error by viewModel.errorLiveData.observeAsState()
                user?.let {
                    DetailsScreen(user = it, onOpenProfileClicked = { url ->
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    })
                }

                error?.let {
                    Text(
                        stringResource(id = R.string.error_fetching_data_message),
                        modifier = Modifier.padding(32.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }


            }
        }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDetailsFragmentDestroyed()
    }
}

@Composable
fun DetailsScreen(user: User, onOpenProfileClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        CircularImage(imageUrl = user.avatar_url)
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {

            Text(text = stringResource(id = R.string.username_s, user.username))

            user.userDetails?.bio?.let {
                Text(text = stringResource(id = R.string.bio_s, it))
            }

            user.userDetails?.public_repos?.let {
                Text(
                    text = stringResource(
                        id = R.string.public_repos_s,
                        it.toString()
                    )
                )
            }

            user.score?.let {
                Text(text = stringResource(id = R.string.score_s, it.toString()))
            }

            user.userDetails?.email?.let {
                Text(text = stringResource(id = R.string.email_s, it.toString()))
            }

            ClickableText(text =
            AnnotatedString(stringResource(R.string.open_full_profile), SpanStyle(Color.Blue)),
                onClick = {
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
