package com.larryyu.zonak_task.data.remote

import com.larryyu.zonak_task.domain.entity.BaseResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.domain.utils.ApiConstants.GET_ALL_CATEGORIES_END_POINT
import com.larryyu.zonak_task.domain.utils.ApiConstants.GET_ALL_NEWS_END_POINT
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsEndPoint {

    @GET(GET_ALL_NEWS_END_POINT)
   suspend fun getAllNews(
        @Path("category") category: String = "general"
    ) : BaseResponse<List<NewsResponseItem>>

    @GET(GET_ALL_CATEGORIES_END_POINT)
   suspend fun getAllCategories() : BaseResponse<List<CategoriesResponseItem>>

}