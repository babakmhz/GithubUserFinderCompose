package com.babakmhz.githubuserfindercompose.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.babakmhz.githubuserfindercompose.R


@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: MutableState<String>,
    onQueryChanged: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp),
                    value = query.value,
                    onValueChange = {
                        query.value = it
                        onQueryChanged(it)
                    },
                    label = { Text(text = stringResource(R.string.type_a_github_username)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hideSoftwareKeyboard()
                        },
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.github_sign),
                            contentDescription = "Search Icon"
                        )
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )

            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@ExperimentalComposeUiApi
@Preview
@Composable
fun previewSearchBar() {
    val query = mutableStateOf("")
    SearchBar(
        query = query,
        onQueryChanged = {},
    )
}