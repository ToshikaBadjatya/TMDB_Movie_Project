package com.example.tmdbmovieproject.data.repo

import android.util.Log
import com.example.tmdbmovieproject.data.api.MovieApi
import com.example.tmdbmovieproject.data.pojo.Movies
import com.example.tmdbmovieproject.data.setup.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class MovieRepo @Inject constructor(val movieApi: MovieApi) {
   val NO_INTERNET=""
    val NO_DATA_FOUND="No data found"

    suspend fun getMovies(): Flow<Response<Movies>> {
        return flow {
            emit(Response.Loading())
            try {
                val response=movieApi.getTrendingMovies()

                if (response.isSuccessful) {
                    if(response.body()!=null){
                        emit(Response.Success(response.body()!!))
                    }else{

                        emit(Response.Error(NO_DATA_FOUND))
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
                Log.e("response","called search $response")

                if (response.isSuccessful) {
                    if(response.body()!=null){
                        emit(Response.Success(response.body()!!))
                    }else{
                        emit(Response.Success(Movies(emptyList())))
                    }

                } else {
                    Log.e("response","called search ${response.message()}")

                    emit(Response.Error("${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("response","called search ${e}")

                emit(Response.Error("${e}"))

            }
        }

    }
}


