package com.larryyu.zonak_task.data.repoimpl

import com.larryyu.zonak_task.data.datasource.PreferenceDataSource
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.repo.PreferencesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PreferencesImplementation @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource,
    moshi: Moshi
) : PreferencesRepository {

    private val newsKey = "news_key"

    private val newsType = Types.newParameterizedType(List::class.java, NewsResponseItem::class.java)
    private val newsAdapter = moshi.adapter<List<NewsResponseItem>>(newsType)

    override suspend fun getNews(): Flow<List<NewsResponseItem>> {
        return preferenceDataSource.getValue(newsKey, "").map { jsonString ->
            if (jsonString is String && jsonString.isNotEmpty()) {
                newsAdapter.fromJson(jsonString) ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun setNews(news: List<NewsResponseItem>) {
        val jsonString = newsAdapter.toJson(news)
        preferenceDataSource.setValue(newsKey, jsonString)
    }
}