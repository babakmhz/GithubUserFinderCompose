package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(modifier) {
        CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))

    }
}
