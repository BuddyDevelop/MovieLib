package bary.apps.moviesLib.data.network.topRatedMovies

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.response.MoviesResponse

interface TopRatedMoviesNetworkDataSource {
    val downloadedTopRatedMovies: LiveData<MoviesResponse>

    suspend fun fetchTopRatedMovies()
}