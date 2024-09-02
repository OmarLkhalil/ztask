package com.larryyu.zonak_task.ui.screens.home.base

import com.larryyu.zonak_task.ui.base.Reducer
import javax.annotation.concurrent.Immutable

@Immutable
sealed class HomeNewsEffect : Reducer.ViewEffect {
    data class NavigateToArticleDetails(val article: String) : HomeNewsEffect()
    data class ShowErrorMessage(val message: String) : HomeNewsEffect()
}
