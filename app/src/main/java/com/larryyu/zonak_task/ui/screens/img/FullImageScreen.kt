package com.larryyu.zonak_task.ui.screens.img

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.larryyu.zonak_task.ui.components.CoilImage


@Composable
fun ShowPhoto(photo: String) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .align(Alignment.Center),
            image = photo,
            contentScale = ContentScale.Fit
        )
    }

}