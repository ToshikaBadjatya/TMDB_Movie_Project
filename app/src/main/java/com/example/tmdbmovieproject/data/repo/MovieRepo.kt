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
    suspend fun getMovies2(): Flow<Response<Movies>> {
        try {
            val url = URL("https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=9fe09909a4eb2ae3a61d16c607df00be")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestMethod("GET")

            // Set timeouts in milliseconds
            urlConnection.setConnectTimeout(10000) // 10 seconds for connection
            urlConnection.setReadTimeout(15000) // 15 seconds for reading
            urlConnection.setRequestProperty("Content-Type", "application/json")

            // Check the response code
            val responseCode: Int = urlConnection.getResponseCode()
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Success
                Log.e("response","$responseCode")
                val `in` = BufferedReader(InputStreamReader(urlConnection.getInputStream()))
                var inputLine: String?
                val content = StringBuilder()
                while (`in`.readLine().also { inputLine = it } != null) {
                    content.append(inputLine)
                }

                // Close streams
                `in`.close()
                Log.e("response","$responseCode")
            } else {
                Log.e("response","$responseCode")
            }
        } catch (e: java.lang.Exception) {
            Log.e("response","catch $e")

            e.printStackTrace()
        } finally {

        }
        return flow {  }
    }
    suspend fun getMovies(): Flow<Response<Movies>> {
        Log.e("response","called")

        return flow {
            emit(Response.Loading())
            try {
                val response=movieApi.getTrendingMovies()
                Log.e("response","${response}")

                if (response.isSuccessful) {
                    if(response.body()!=null){
                        Log.e("response","${response.body()}")

//                        emit(Response.Success(response.body()!!))
                    }else{
                        Log.e("response","null")

                        emit(Response.Error(NO_DATA_FOUND))
                    }

                } else {
                    Log.e("response","${response.message()}")

                    emit(Response.Error("${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("response","$e")

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


