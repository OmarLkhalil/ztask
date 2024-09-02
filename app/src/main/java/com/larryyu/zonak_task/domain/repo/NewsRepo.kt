package com.larryyu.zonak_task.domain.repo

import com.larryyu.zonak_task.domain.entity.BaseResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    suspend fun onGetAllNews() : Flow<DataState<BaseResponse<List<NewsResponseItem>>>>

    suspend fun onGetAllCategories() : Flow<DataState<BaseResponse<List<CategoriesResponseItem>>>>
}