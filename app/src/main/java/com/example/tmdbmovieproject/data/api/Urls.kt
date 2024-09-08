package com.example.tmdbmovieproject.data.api

object Urls {
    const val API_KEY="9fe09909a4eb2ae3a61d16c607df00be"
    const val BASE_URL="https://api.themoviedb.org/"
    const val VERSION= BASE_URL+"3/"
    const val SEARCH= VERSION+"search/movie"
    const val TRENDING= VERSION+"trending/movie/day?language=en-US"
    const val IMAGE="https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
    const val ACCESS_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZmUwOTkwOWE0ZWIyYWUzYTYxZDE2YzYwN2RmMDBiZSIsIm5iZiI6MTcyNTcwNDUyMi43NTg2MDIsInN1YiI6IjYyMDkzMTNjMWQ3OGYyMDBkYjQ3NTZjNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.L-YtbBZLctlT7L8Iomnrgzvq91hYOrgXUOlk4Izo5Jg"
    const val TIME_OUT:Long=5000
}
