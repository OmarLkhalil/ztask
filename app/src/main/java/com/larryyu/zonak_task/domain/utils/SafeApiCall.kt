package com.larryyu.zonak_task.domain.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.larryyu.zonak_task.domain.entity.BaseResponse
import com.larryyu.zonak_task.domain.utils.FailRequestCode.BLOCKED
import com.larryyu.zonak_task.domain.utils.FailRequestCode.EXCEPTION
import com.larryyu.zonak_task.domain.utils.FailRequestCode.UN_AUTH
import com.larryyu.zonak_task.domain.utils.FailRequestCode.UN_PROCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException


const val NETWORK_TIMEOUT: Long = 6000000000

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Flow<DataState<T>> = flow {

    withTimeout(NETWORK_TIMEOUT) {
        val response = apiCall.invoke()
        emit(handleSuccess(response))
    }

}.onStart { emit(DataState.Loading) }.catch { emit(handleError(it)) }.flowOn(Dispatchers.IO)


fun <T> handleSuccess(response: T): DataState<T> {
    if (response != null) return DataState.Success(response)

    return DataState.Error(Exception("Response is null"))
}


fun <T> handleError(it: Throwable): DataState<T> {
    it.printStackTrace()
    return when (it) {
        is TimeoutCancellationException -> {
            DataState.Error(NetworkExceptions.TimeoutException)
        }

        is UnknownHostException -> {
            DataState.Error(NetworkExceptions.NoInternetError)
        }

        is IOException -> {
            DataState.Error(NetworkExceptions.NetworkError)
        }

        is HttpException -> {
            DataState.Error(convertErrorBody(it))
        }

        else -> {
            DataState.Error(NetworkExceptions.UnknownException)
        }
    }

}

fun convertErrorBody(throwable: HttpException): Exception {
    val errorBody = throwable.response()?.errorBody()?.charStream()
    val response: BaseResponse<*>?

    try {
        response = Gson().fromJson(errorBody, BaseResponse::class.java)
    } catch (e: JsonSyntaxException) {
        return NetworkExceptions.UnknownException
    }

    return when (throwable.code()) {
        UN_AUTH, BLOCKED -> NetworkExceptions.AuthorizationException
        EXCEPTION -> NetworkExceptions.ServerException
        UN_PROCESS -> NetworkExceptions.UnProcessableContent(response.status)
        else -> NetworkExceptions.UnknownException
    }
}