package com.example.tmdbmovieproject.data.repo

import android.util.Log
import com.example.tmdbmovieproject.data.api.MovieApi
import com.example.tmdbmovieproject.data.pojo.Movies
import com.example.tmdbmovieproject.data.setup.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepo @Inject constructor(val movieApi: MovieApi) {
    suspend fun getMovies(): Flow<Response<Movies>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = movieApi.getTrendingMovies()
                Log.e("response","$response")
                if (response.isSuccessful) {
                    if(response.body()!=null){
                        emit(Response.Success(response.body()!!))
                    }else{
                        emit(Response.Error("No data Found"))
                    }

                } else {
                    emit(Response.Error("${response.message()}"))
                }
            } catch (e: Exception) {

                emit(Response.Error("${e}"))

            }
        }

    }
    suspend fun searchMovies(keyWord:String): Flow<Response<Movies>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = movieApi.searchMovies(keyWord)
                if (response.isSuccessful) {
                    if(response.body()!=null){
                        emit(Response.Success(response.body()!!))
                    }else{
                        emit(Response.Error("No data Found"))
                    }

                } else {
                    emit(Response.Error("${response.message()}"))
                }
            } catch (e: Exception) {

                emit(Response.Error("${e}"))

            }
        }

    }
}