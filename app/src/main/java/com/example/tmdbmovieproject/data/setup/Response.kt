package com.example.tmdbmovieproject.data.setup

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Response<T>(data)
    class IDLE<T>(): Response<T>(null)
    class Error<T>(message: String): Response<T>(message= message)
    class Loading<T>(): Response<T>()
}