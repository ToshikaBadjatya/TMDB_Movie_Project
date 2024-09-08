package com.example.tmdbmovieproject.screens

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tmdbmovieproject.data.api.Urls
import com.example.tmdbmovieproject.data.pojo.Movie
import com.example.tmdbmovieproject.data.pojo.Movies
import com.example.tmdbmovieproject.data.setup.Response
import com.example.tmdbmovieproject.navigation.Destinations
import com.example.tmdbmovieproject.viewmodel.MovieViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import coil.ImageLoader


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListing(navController: NavHostController, movieViewModel: MovieViewModel) {
    val activity = LocalContext.current as? Activity
    val loadState = remember {
        mutableStateOf(false)
    }
    val movieResult by movieViewModel._moviesResult.collectAsState()
    val query = remember {
        mutableStateOf<String>("")
    }
    val doSearch=remember {
        mutableStateOf(false)
    }
    fetchData(movieViewModel)
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(horizontal = 16.dp))
        ) {
            searchComposeable(query.value?:"",{
                query.value = it
            },{
                doSearch.value=true
            })
            handleResponse(movieResult,movieViewModel,navController)
        }
        if (loadState.value) {

        }
    }
    LaunchedEffect(doSearch.value) {
        if(query.value.isEmpty()){
            movieViewModel.getMovies()
        }
        else{
            movieResult.data?.let {
                val movies=it.results.filter { it.title.toLowerCase().contains(query.value.toLowerCase()) }
                movieViewModel.setMovies(movies)
            }
        }
        doSearch.value=false
    }
    BackHandler {
        activity?.finish()
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchComposeable(value: String, valuechange: (String) -> Unit, queryDataCallback: () -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp), shape = RoundedCornerShape(10.dp),
        placeholder = { Text("Search movies") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,  // Removes the bottom line when focused
            unfocusedIndicatorColor = Color.Transparent  // Removes the bottom line when not focused
        ),
        value = value,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,    // Set the keyboard type (text, number, etc.)
            imeAction = ImeAction.Search           // Set the IME action (e.g., Done, Next, Search)
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                queryDataCallback.invoke()
            }
        ),
        onValueChange = { valuechange.invoke(it) }, trailingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "", Modifier.clickable {
                queryDataCallback.invoke()
            })
        })

}
@Composable
fun handleResponse(
    movieResult: Response<Movies>,
    movieViewModel: MovieViewModel,
    navController: NavHostController
) {
    val activity = LocalContext.current as? Activity

    when(movieResult){
        is Response.Success->{
            Log.e("response","${movieResult.data!!.results}")
            showListing(movieResult.data!!.results) {
                movieViewModel.setCurrentMovie(it)
                navController.navigate(Destinations.MovieDetail.path)
            }
        }
        is Response.Loading->{
           Loader()
        }
        is Response.Error->{
            Toast.makeText(activity, "${movieResult.message}", Toast.LENGTH_SHORT).show()

        }
        else -> {}
    }
}

@Composable
fun Loader() {
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
fun movieItem(movie: Movie) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column() {

            Image(
               painter = rememberAsyncImagePainter(model = "${Urls.IMAGE}${movie.posterPath}"), contentDescription = "", modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                movie.title, modifier = Modifier
                    .padding(start = 6.dp)
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp)
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun fetchData(movieViewModel: MovieViewModel) {
    LaunchedEffect(Unit) {
        delay(1000)
        movieViewModel.getMovies()
    }
}





