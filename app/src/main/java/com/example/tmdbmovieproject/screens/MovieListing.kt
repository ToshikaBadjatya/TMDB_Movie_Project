package com.example.tmdbmovieproject.screens

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tmdbmovieproject.data.pojo.Movie
import com.example.tmdbmovieproject.data.setup.Response
import com.example.tmdbmovieproject.navigation.Destinations
import com.example.tmdbmovieproject.viewmodel.MovieViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListing(navController: NavHostController,movieViewModel: MovieViewModel) {
    val activity = LocalContext.current as? Activity

    val loadState= remember {
        mutableStateOf(false)
    }
    val movieList = remember {
        mutableStateOf<List<Movie>>(
            listOf(Movie(2, "cdfcdf", "cdcec", "csdcd", "cddc", "cdfv"),
                Movie(1, "cdfcdf", "cdcec", "csdcd", "cddc", "cdfv")))
    }
    val query = remember {
        mutableStateOf("")
    }
    fetchData(movieViewModel)
    handleResponse(movieViewModel = movieViewModel, loadingCallback = {
        loadState.value=it
    }, successCallback = {

    }, errorCallback = {})

    Surface {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(horizontal = 16.dp))) {
            searchComposeable(query.value){
                query.value=it
            }
            showListing(movieList.value) {
                movieViewModel.setCurrentMovie(it)
                navController.navigate(Destinations.MovieDetail.path)
            }
        }
        LaunchedEffect(loadState){
            if(loadState.value){

            }
        }

    }
    BackHandler {
        activity?.finish()
    }



}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchComposeable(value: String,valuechange:(String)->Unit) {
    TextField(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp), shape = RoundedCornerShape(10.dp),
        placeholder = {Text("Search movies")},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,  // Removes the bottom line when focused
            unfocusedIndicatorColor = Color.Transparent  // Removes the bottom line when not focused
        )
        , value = value, onValueChange = {valuechange.invoke(it)}, trailingIcon = {
        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
    })

}

@Composable
fun showListing(list: List<Movie>, onItemClick: (Movie) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(list) { movie ->
            Surface(onClick = { onItemClick.invoke(movie) }) {
                movieItem(movie)
            }
        }
    }
}

@Composable
fun movieItem(movie:Movie) {
    Card (modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)){
        Column() {
            Image(imageVector = Icons.Filled.KeyboardArrowUp,"",modifier= Modifier
                .height(200.dp)
                .fillMaxWidth(), contentScale = ContentScale.FillBounds)
            Text(movie.title, modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp))
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun fetchData(movieViewModel: MovieViewModel) {
    LaunchedEffect(Unit){
        delay(1000)
        movieViewModel.getMovies()
    }
}
@Composable
fun handleResponse(movieViewModel: MovieViewModel, loadingCallback:(Boolean)->Unit, successCallback:(movies:List<Movie>)->Unit, errorCallback:()->Unit) {
    val movieList=movieViewModel.moviesResult.collectAsState()
    val response=movieList.value
    when(response){
        is Response.Loading->{
         loadingCallback.invoke(true)
        }
        is Response.Success->{
            loadingCallback.invoke(false)
            successCallback.invoke(response.data!!.results)

        }
        is Response.Error->{
            loadingCallback.invoke(false)

        }
        is Response.IDLE->{}
    }

}

