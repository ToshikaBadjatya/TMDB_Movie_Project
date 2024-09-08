package com.example.tmdbmovieproject.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tmdbmovieproject.screens.MovieDetail
import com.example.tmdbmovieproject.screens.MovieListing
import com.example.tmdbmovieproject.viewmodel.MovieViewModel

@Composable
fun ProjectNavigation(navController: NavHostController){
    val movieViewModel:MovieViewModel= hiltViewModel()
    NavHost(modifier = Modifier.fillMaxSize(), navController = navController, startDestination = Destinations.MovieListing.path ){
        composable(Destinations.MovieListing.path){
            MovieListing(navController, movieViewModel)
        }
        composable(Destinations.MovieDetail.path){
            MovieDetail(navController,movieViewModel)
        }

    }
}