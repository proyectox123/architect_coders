package com.example.android.domain.result

import java.io.IOException

sealed class DataResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> DataResult<T>, errorMessage: String): DataResult<T> = try {
    call.invoke()
} catch (e: Exception) {
    DataResult.Error(IOException(errorMessage, e))
}