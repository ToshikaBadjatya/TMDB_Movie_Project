package com.example.tmdbmovieproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tmdbmovieproject.data.api.Urls
import com.example.tmdbmovieproject.data.pojo.Movie
import com.example.tmdbmovieproject.navigation.Destinations
import com.example.tmdbmovieproject.viewmodel.MovieViewModel

@Composable
fun MovieDetail(navController: NavHostController, movieViewModel: MovieViewModel) {
Scaffold(modifier = Modifier.padding(top = 20.dp,  start = 16.dp,end=16.dp), topBar = {
 Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = "",modifier=Modifier.padding(bottom = 10.dp).clickable {
movieViewModel.resetCurrentMovie()
  navController.navigate(Destinations.MovieListing.path)
 })
}) {padding->

  movieViewModel.getCurrentMovie()?.let {
   showMovieDetail(it,padding)
  }
 }
}


@Composable
fun showMovieDetail(it: Movie, padding: PaddingValues) {
 Column(modifier= Modifier
  .fillMaxSize()
  .padding(padding)) {
  Image(
   painter = rememberAsyncImagePainter(model = Urls.IMAGE), contentDescription = "", modifier = Modifier
    .height(400.dp)
    .fillMaxWidth(),
   contentScale = ContentScale.Crop
  )
  Text(
   "${it.title}", modifier = Modifier
    .background(Color.White)
    .align(Alignment.CenterHorizontally)
    .padding(vertical = 10.dp)
  )
  Text(
   "${it.overview}", modifier = Modifier
    .background(Color.White)
    .align(Alignment.CenterHorizontally)
    .padding(vertical = 10.dp)
  )
 }
}
