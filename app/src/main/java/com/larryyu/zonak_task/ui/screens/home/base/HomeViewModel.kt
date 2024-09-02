package com.larryyu.zonak_task.ui.screens.home.base

import androidx.lifecycle.viewModelScope
import com.larryyu.zonak_task.domain.repo.PreferencesRepository
import com.larryyu.zonak_task.domain.usecase.GetAllNewsUseCase
import com.larryyu.zonak_task.domain.usecase.GetCategoriesUseCase
import com.larryyu.zonak_task.domain.utils.DataState
import com.larryyu.zonak_task.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeNewsViewModel @Inject constructor(
    reducer: HomeNewsReducer,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getAllCategoryUseCase: GetCategoriesUseCase,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<HomeNewsState, HomeNewsEvent, HomeNewsEffect>(

    initialState = HomeNewsState(
        categories = emptyList(),
        articles = emptyList(),
        isLoading = false
    ),
    reducer = reducer
) {


    fun loadCategories() {
        viewModelScope.launch {
            sendEvent(HomeNewsEvent.LoadCategories)
            getAllCategoryUseCase().collectLatest { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val newState = dataState.data.sources?.let {
                            state.value.copy(
                                categories = it,
                                isLoading = false
                            )
                        }
                        if (newState != null) {
                            _state.emit(newState)
                        }
                    }

                    is DataState.Error -> {
                        _state.emit(state.value.copy(isLoading = false))
                        sendEffect(
                            HomeNewsEffect.ShowErrorMessage(
                                dataState.throwable.message ?: "An error occurred"
                            )
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun loadArticles(categoryId: String) {
        viewModelScope.launch {
            sendEvent(HomeNewsEvent.LoadArticles(categoryId))


                fetchArticlesFromApi(categoryId)

        }
    }

    private suspend fun fetchArticlesFromApi(categoryId: String) {
        getAllNewsUseCase(categoryId).collectLatest { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val newArticles = dataState.data.articles ?: emptyList()

                    preferencesRepository.setNews(newArticles)

                    _state.emit(state.value.copy(articles = newArticles, isLoading = false))
                }

                is DataState.Loading -> {
                    _state.emit(state.value.copy(isLoading = true))
                }

                is DataState.Error -> {
                    _state.emit(state.value.copy(isLoading = false))
                    sendEffect(
                        HomeNewsEffect.ShowErrorMessage(
                            dataState.throwable.message ?: "An error occurred"
                        )
                    )
                    val cachedArticles = preferencesRepository.getNews().first()
                    _state.emit(state.value.copy(articles = cachedArticles, isLoading = false))
                }

                DataState.Idle -> {}
            }
        }
    }

    fun selectCategory(categoryId: String) {
        sendEvent(HomeNewsEvent.SelectCategory(categoryId))
        loadArticles(categoryId)
    }

    fun viewArticle(articleId: String) {
        sendEventForEffect(HomeNewsEvent.ViewArticle(articleId))
    }
}
