package com.larryyu.zonak_task.domain.models.all_news_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseItem(
    @Json(name = "author")
    val author: String? = "",
    @Json(name = "content")
    val content: String? = "",
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "publishedAt")
    val publishedAt: String? = "",
    @Json(name = "source")
    val source: Source? = Source(),
    @Json(name = "title")
    val title: String? = "",
    @Json(name = "url")
    val url: String? = "",
    @Json(name = "urlToImage")
    val urlToImage: String? = "",
)