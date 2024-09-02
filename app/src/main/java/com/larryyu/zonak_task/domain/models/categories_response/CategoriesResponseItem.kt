package com.larryyu.zonak_task.domain.models.categories_response


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponseItem(
    @field:SerializedName("category")
    val category: String? = "",
    @field:SerializedName("country")
    val country: String? = "",
    @field:SerializedName( "description")
    val description: String? = "",
    val isSelected : Boolean = false,
    @field:SerializedName( "id")
    val id: String? = "",
    @field:SerializedName( "language")
    val language: String? = "",
    @field:SerializedName("name")
    val name: String? = "",
    @field:SerializedName( "url")
    val url: String? = "",
)