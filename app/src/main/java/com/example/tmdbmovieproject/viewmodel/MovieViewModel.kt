package com.example.tmdbmovieproject.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbmovieproject.data.pojo.Movie
import com.example.tmdbmovieproject.data.pojo.Movies
import com.example.tmdbmovieproject.data.repo.MovieRepo
import com.example.tmdbmovieproject.data.setup.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepo: MovieRepo):ViewModel() {
     val moviesResult= MutableStateFlow<Response<Movies>>(Response.IDLE())

    private val searchMoviesResult= MutableStateFlow<Response<Movies>>(Response.IDLE())
    val _searchMoviesResult=searchMoviesResult.asStateFlow()
    private val currentMovie= MutableStateFlow<Movie?>(null)
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMovies() {
        viewModelScope.launch {
            moviesResult.value= Response.IDLE()
            withContext(Dispatchers.IO) {
                movieRepo.getMovies().collect { result ->
                    Log.e("response","$result")

                    moviesResult.value = result
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun searchMovies(keyWord:String) {
        viewModelScope.launch {
            searchMoviesResult.value= Response.IDLE()
            withContext(Dispatchers.IO) {
                movieRepo.searchMovies(keyWord).collect { result ->
                    searchMoviesResult.value = result
                }
            }
        }
    }

    fun resetCurrentMovie() {
       currentMovie.value=null
    }
    fun setCurrentMovie(movie: Movie){
        currentMovie.value=movie
    }
    fun getCurrentMovie()=currentMovie.value
}