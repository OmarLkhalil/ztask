package com.larryyu.zonak_task.ui.screens.home.base

import com.larryyu.zonak_task.ui.base.Reducer

class HomeNewsReducer : Reducer<HomeNewsState, HomeNewsEvent, HomeNewsEffect> {

    override fun reduce(
        previousState: HomeNewsState,
        event: HomeNewsEvent
    ): Pair<HomeNewsState, HomeNewsEffect?> {
        return when (event) {
            is HomeNewsEvent.LoadCategories -> {
                previousState.copy(isLoading = true) to null
            }

            is HomeNewsEvent.LoadArticles -> {
                previousState.copy(isLoading = true) to null
            }

            is HomeNewsEvent.SelectCategory -> {
                val updatedCategories = previousState.categories.map {
                    if (it.id == event.categoryId) it.copy(isSelected = true) else it.copy(isSelected = false)
                }
                previousState.copy(categories = updatedCategories) to null
            }

            is HomeNewsEvent.ViewArticle -> {
                previousState to HomeNewsEffect.NavigateToArticleDetails(event.article)
            }
        }
    }
}
