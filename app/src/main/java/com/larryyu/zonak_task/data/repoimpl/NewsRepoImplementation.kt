package com.larryyu.zonak_task.data.repoimpl

import com.larryyu.zonak_task.data.remote.NewsEndPoint
import com.larryyu.zonak_task.domain.entity.BaseResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.domain.repo.NewsRepo
import com.larryyu.zonak_task.domain.utils.DataState
import com.larryyu.zonak_task.domain.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NewsRepoImplementation @Inject constructor(
    private val newsEndPoint: NewsEndPoint
) : NewsRepo {

    override suspend fun onGetAllNews(category:String): Flow<DataState<BaseResponse<List<NewsResponseItem>>>> =
        safeApiCall {
            newsEndPoint.getAllNews(category)
        }

    override suspend fun onGetAllCategories(): Flow<DataState<BaseResponse<List<CategoriesResponseItem>>>> =
        safeApiCall {
            newsEndPoint.getAllCategories()
        }

}