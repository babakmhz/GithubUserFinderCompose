package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.babakmhz.githubuserfindercompose.R
import kotlinx.coroutines.delay


@Composable
fun LoadingWithRetryIndicator(modifier: Modifier = Modifier) {
    Column(modifier) {
        CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))

    }
}
