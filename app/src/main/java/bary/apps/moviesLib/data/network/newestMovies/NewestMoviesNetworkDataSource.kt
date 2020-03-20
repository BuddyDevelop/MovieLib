package bary.apps.moviesLib.data.network.newestMovies

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.FetchMovies
import bary.apps.moviesLib.data.network.response.MoviesResponse

interface NewestMoviesNetworkDataSource : FetchMovies {
    val downloadedNewestMovies: LiveData<MoviesResponse>
}