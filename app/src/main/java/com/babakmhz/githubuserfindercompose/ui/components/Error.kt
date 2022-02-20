package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babakmhz.githubuserfindercompose.R

@Composable
fun ConnectionError(){
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
                text = stringResource(R.string.retry),
                fontSize = 18.sp
            )
        }
    }

}