package com.larryyu.zonak_task.domain.utils

import com.google.gson.Gson


fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}