package com.larryyu.zonak_task.ui.screens.home

import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.domain.utils.toJson
import com.larryyu.zonak_task.ui.navigation.Routes
import com.larryyu.zonak_task.ui.screens.home.base.HomeNewsEffect
import com.larryyu.zonak_task.ui.screens.home.base.HomeNewsState
import com.larryyu.zonak_task.ui.screens.home.base.HomeNewsViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeNewsScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: HomeNewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsState(null)
    val context = LocalContext.current


    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        viewModel.selectCategory("general")
        viewModel.loadCategories()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(Unit) {
        viewModel.selectCategory("general")
        viewModel.loadCategories()
    }
    LaunchedEffect(effect) {
        effect.let { action ->
            when (action) {
                is HomeNewsEffect.NavigateToArticleDetails -> {
                    navController.navigate(Routes.Details.createRoute(action.article))
                }

                is HomeNewsEffect.ShowErrorMessage -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
    ) {
        if (!refreshing) {
            HomeNewsContent(
                state = state,
                onCategorySelected = viewModel::selectCategory,
                onArticleClicked = viewModel::viewArticle,
                animatedContentScope = animatedContentScope,
                sharedTransitionScope = sharedTransitionScope
            )
        }
        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeNewsContent(
    state: HomeNewsState,
    onCategorySelected: (String) -> Unit,
    onArticleClicked: (String) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        { CircularProgressIndicator() }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.sdp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.sdp)
            ) {
                items(state.categories) { category ->
                    TabItem(category, onCategorySelected)
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                items(state.articles) { article ->
                    NewsItem(
                        article,
                        onArticleClicked,
                        animatedContentScope,
                        sharedTransitionScope
                    )
                }
            }
        }
    }
}

@Composable
fun TabItem(
    cate: CategoriesResponseItem,
    onCategorySelected: (String) -> Unit
) {
    val color = if (cate.isSelected) Color.Red else Color.DarkGray
    Surface(
        color = color,
        shape = RoundedCornerShape(20.sdp),
        modifier = Modifier
            .padding(horizontal = 5.sdp)
            .height(30.sdp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = cate.name ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .clickable { onCategorySelected(cate.category ?: "") }
            )
        }

    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NewsItem(
    newsItem: NewsResponseItem,
    onArticleClicked: (String) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {

    val item = NewsResponseItem(
        urlToImage = URLEncoder.encode(newsItem.urlToImage, StandardCharsets.UTF_8.toString()),
        title = newsItem.title,
        url = URLEncoder.encode(newsItem.url, StandardCharsets.UTF_8.toString()),
        description = newsItem.description,
        content = newsItem.content
    )
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(vertical = 5.sdp, horizontal = 10.sdp)
            .clickable { onArticleClicked(item.toJson()) }
    ) {
        with(sharedTransitionScope) {
            SubcomposeAsyncImage(
                model = newsItem.urlToImage,
                contentDescription = "news item image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = newsItem.title ?: ""),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .width(80.sdp)
                    .clip(RoundedCornerShape(10.sdp))
                    .height(100.sdp)
            )
        }
        Spacer(modifier = Modifier.width(10.sdp))
        Column() {
            Text(
                text = newsItem.title ?: "",
                fontSize = 12.ssp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = newsItem.description ?: "",
                fontSize = 9.ssp
            )
        }

    }
}
