package com.larryyu.zonak_task.domain.utils

sealed class NetworkExceptions(val excpetion: String? = "") : Exception() {
    data object UnknownException : NetworkExceptions("Unknown Error")
    data object ServerException : NetworkExceptions("Server Error")
    data object NetworkError : NetworkExceptions("Network Error")
    data object NoInternetError : NetworkExceptions("No Internet Error")
    data object NotFoundException : NetworkExceptions("Not Found Error")
    data object TimeoutException : NetworkExceptions("Timeout Error")
    data class UnProcessableContent(val msg: String) : NetworkExceptions(msg)
    data object ConnectionException : NetworkExceptions("Connection Error")
    data object AuthorizationException : NetworkExceptions("Authorization Error")
    data class CustomException(val msg: String) : NetworkExceptions(msg)
    data class NeedActiveException(val msg: String) : NetworkExceptions(msg)
}