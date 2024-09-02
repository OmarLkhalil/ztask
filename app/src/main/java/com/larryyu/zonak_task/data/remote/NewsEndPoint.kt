package com.larryyu.zonak_task.data.remote

import com.larryyu.zonak_task.domain.entity.BaseResponse
import com.larryyu.zonak_task.domain.models.all_news_response.NewsResponseItem
import com.larryyu.zonak_task.domain.models.categories_response.CategoriesResponseItem
import com.larryyu.zonak_task.domain.utils.ApiConstants.GET_ALL_CATEGORIES_END_POINT
import com.larryyu.zonak_task.domain.utils.ApiConstants.GET_ALL_NEWS_END_POINT
import retrofit2.http.GET

interface NewsEndPoint {

    @GET(GET_ALL_NEWS_END_POINT)
    fun getAllNews() : BaseResponse<List<NewsResponseItem>>

    @GET(GET_ALL_CATEGORIES_END_POINT)
    fun getAllCategories() : BaseResponse<List<CategoriesResponseItem>>

}