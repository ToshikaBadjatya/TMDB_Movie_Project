package com.example.tmdbmovieproject.data.pojo

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName
data class Movies(val results:List<Movie>)
data class Movie(
    val id: String,
    val title: String,
    val overview: String, @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String
)

