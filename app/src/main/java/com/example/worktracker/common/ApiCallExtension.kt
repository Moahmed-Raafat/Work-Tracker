package com.example.worktracker.common

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import kotlin.jvm.java

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    try {
        return apiCall()
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val message = if (!errorBody.isNullOrEmpty()) {
            try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) {
                null
            }
        } else null
        throw DomainException(message ?: "Unexpected server error")
    } catch (e: IOException) {
        throw DomainException("Network error. Check your internet connection")
    }
}
