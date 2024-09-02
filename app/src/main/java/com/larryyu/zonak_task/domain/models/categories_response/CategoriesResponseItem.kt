package com.larryyu.zonak_task.domain.models.categories_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponseItem(
    @Json(name = "category")
    val category: String? = "",
    @Json(name = "country")
    val country: String? = "",
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "id")
    val id: String? = "",
    @Json(name = "language")
    val language: String? = "",
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "url")
    val url: String? = "",
)