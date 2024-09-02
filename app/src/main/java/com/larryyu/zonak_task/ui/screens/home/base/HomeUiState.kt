package com.larryyu.zonak_task.ui.screens.home.base

import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.ui.base.Reducer
import javax.annotation.concurrent.Immutable

@Immutable

data class HomeNewsState(
    val categories: List<CategoriesResponseItem> = emptyList(),
    val articles: List<NewsResponseItem> = emptyList(),
    val isLoading: Boolean = false,
) : Reducer.ViewState