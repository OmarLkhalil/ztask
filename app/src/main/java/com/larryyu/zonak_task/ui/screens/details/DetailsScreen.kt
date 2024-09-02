package com.larryyu.zonak_task.ui.screens.details

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat.startActivity
import coil.compose.SubcomposeAsyncImage
import com.larryyu.zonak_task.R
import com.larryyu.zonak_task.ui.extensions.npClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    name: String,
    img: String,
    url: String,
    description: String,
    content: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onImageClick: (img: String) -> Unit,
) {

    val openUrlIntent = Intent(Intent.ACTION_VIEW)
    openUrlIntent.data = android.net.Uri.parse(url)
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Box {
            with(sharedTransitionScope) {
                SubcomposeAsyncImage(
                    model = img,
                    contentDescription = "news item image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = name),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .npClickable {
                            onImageClick(URLEncoder.encode(img, StandardCharsets.UTF_8.toString()))
                        }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.sdp))
                        .height(200.sdp)
                )
            }
            ShareButton(
                text = url,
                context = context,
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(top = 10.sdp)
                    .clip(RoundedCornerShape(20.sdp))
            )
        }

        Text(
            text = name,
            modifier = Modifier.padding(start = 5.sdp, top = 10.sdp),
            fontSize = 14.ssp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.description),
                fontSize = 16.ssp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.see_all),
                modifier = Modifier
                    .weight(1f)
                    .npClickable {
                        startActivity(context, openUrlIntent, null)
                    },
                textAlign = TextAlign.End,
                color = Color.Blue,
                fontSize = 10.ssp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = description,
            modifier = Modifier.padding(start = 5.sdp, top = 10.sdp),
            fontSize = 10.ssp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.content),
            fontSize = 16.ssp,
            modifier = Modifier.padding(start = 5.sdp),
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .height(100.sdp)
                .padding(5.sdp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = content,
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ShareButton(text: String, context: Context, modifier: Modifier) {

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
        onClick = {
            startActivity(context, shareIntent, null)
        }, modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.share_text),
            fontSize = 12.ssp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(5.sdp))
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "",
            tint = Color.White
        )
    }
}