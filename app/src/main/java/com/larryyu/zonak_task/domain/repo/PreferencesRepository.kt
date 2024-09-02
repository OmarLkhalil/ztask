package com.larryyu.zonak_task.domain.repo

import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun getNews(): Flow<List<NewsResponseItem>>
    suspend fun setNews(news: List<NewsResponseItem>)
}