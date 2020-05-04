package bary.apps.moviesLib.data.network.popularMovies

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.response.MoviesResponse

interface PopularMoviesNetworkDataSource {
    val downloadedPopularMovies: LiveData<MoviesResponse>

    suspend fun fetchPopularMovies(page: Int)
}