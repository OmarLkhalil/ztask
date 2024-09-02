package com.larryyu.zonak_task.ui.screens.home.base

import com.larryyu.zonak_task.ui.base.Reducer
import javax.annotation.concurrent.Immutable

@Immutable
sealed class HomeNewsEvent : Reducer.ViewEvent {
    data object LoadCategories : HomeNewsEvent()
    data class LoadArticles(val categoryId: String) : HomeNewsEvent()
    data class SelectCategory(val categoryId: String) : HomeNewsEvent()
    data class ViewArticle(val article: String) : HomeNewsEvent()
}
