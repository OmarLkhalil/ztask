package com.larryyu.zonak_task.domain.utils

import java.lang.Exception

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val throwable: Exception) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data object Idle : DataState<Nothing>()
}