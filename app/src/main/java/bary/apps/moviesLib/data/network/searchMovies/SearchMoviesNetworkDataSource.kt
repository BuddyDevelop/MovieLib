package bary.apps.moviesLib.data.network.searchMovies

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.response.MoviesResponse

interface SearchMoviesNetworkDataSource {
    val downloadedMovies: LiveData<MoviesResponse>

    suspend fun fetchMoviesByName(movieName: String)
}