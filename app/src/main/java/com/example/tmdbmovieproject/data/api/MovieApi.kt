package com.example.tmdbmovieproject.data.api

import com.example.tmdbmovieproject.data.pojo.Movie
import com.example.tmdbmovieproject.data.pojo.Movies
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieApi {
    @GET(Urls.TRENDING)
    suspend fun getTrendingMovies(@Query("api_key") apiKey:String=Urls.API_KEY):retrofit2.Response<Movies>
    @POST(Urls.SEARCH)
    suspend fun searchMovies(@Query("query") query:String):retrofit2.Response<Movies>

}