package com.larryyu.zonak_task.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    image: Any? = null,
    contentDescription: String? = "",
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}