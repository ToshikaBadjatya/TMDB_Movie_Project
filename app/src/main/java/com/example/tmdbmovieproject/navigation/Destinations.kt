package com.example.tmdbmovieproject.navigation

sealed class Destinations(open val displayName:String,open val path:String) {
    object MovieListing:Destinations(displayName = "Movie Listing", path = "movie_listing_screen")
    object MovieDetail:Destinations(displayName = "home", path = "movie_detail_screen")


}