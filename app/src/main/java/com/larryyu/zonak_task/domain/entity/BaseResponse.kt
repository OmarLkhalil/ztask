package com.larryyu.zonak_task.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "status") val status: String,
    @Json(name = "articles") val articles: T? = null,
    @Json(name = "sources") val sources: T? = null,
    @Json(name = "totalResults") val msg: Int? = 0
) : Serializable
