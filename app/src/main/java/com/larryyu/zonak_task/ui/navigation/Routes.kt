package com.larryyu.zonak_task.ui.navigation


sealed class Routes(val route: String) {

    data object Home : Routes("home_root")

    data object Details : Routes("details_root/{newsItem}"){
        fun createRoute(
            newsItem: String,
        ) = "details_root/$newsItem"
    }

    data object FullImageShow : Routes("FullImageShow/{image}") {
        fun createRoute(image: String) = "FullImageShow/$image"
    }

}
