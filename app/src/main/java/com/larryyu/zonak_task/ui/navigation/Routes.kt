package com.larryyu.zonak_task.ui.navigation

import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem

sealed class Routes(val route: String) {

    data object Home : Routes("home_root")

    data object Details : Routes("details_root"){
        fun createRoute(
            newsItem: NewsResponseItem,
        ) = "details_root/$newsItem"
    }
}
