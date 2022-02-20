package com.babakmhz.githubuserfindercompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.babakmhz.githubuserfindercompose.R


@Composable
fun CircularImage(imageUrl: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            elevation = 12.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        placeholder(R.drawable.ic_launcher_background)
                        crossfade(true)
                        crossfade(600)
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())

                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Preview
@Composable
fun previewCircleImage() {
    CircularImage(imageUrl = "https://avatars.githubusercontent.com/u/31562825?v=4")
}