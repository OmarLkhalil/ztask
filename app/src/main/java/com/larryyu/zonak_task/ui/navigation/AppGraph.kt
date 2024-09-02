package com.larryyu.zonak_task.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.utils.fromJson
import com.larryyu.zonak_task.ui.screens.details.DetailsScreen
import com.larryyu.zonak_task.ui.screens.home.HomeNewsScreen
import com.larryyu.zonak_task.ui.screens.img.ShowPhoto

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppGraph(
    navController: NavHostController
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(route = Routes.Home.route) {
                homeScreen(navController, this@SharedTransitionLayout, this)
            }
            composable(route = Routes.Details.route) { it ->
                val newsItem = it.arguments?.getString("newsItem")?.fromJson<NewsResponseItem>()
                if (newsItem != null) {
                    detailsScreen(navController, newsItem, this@SharedTransitionLayout, this)
                }
            }
            fullImageShow()
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun homeScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    HomeNewsScreen(
        navController,
        sharedTransitionScope,
        animatedContentScope
    )

}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun detailsScreen(
    navController: NavController, newsItem: NewsResponseItem,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    DetailsScreen(
        img = newsItem.urlToImage ?: "",
        name = newsItem.title ?: "",
        description = newsItem.description ?: "",
        content = newsItem.content ?: "",
        url = newsItem.url ?: "",
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
    ) {
        navController.navigate(Routes.FullImageShow.createRoute(it))
    }

}

private fun NavGraphBuilder.fullImageShow() {
    composable(route = Routes.FullImageShow.route) {
        val img = it.arguments?.getString("image")
        if (img != null) {
            ShowPhoto(img)
        }
    }
}