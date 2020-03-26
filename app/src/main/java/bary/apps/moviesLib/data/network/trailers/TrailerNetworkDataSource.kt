package bary.apps.moviesLib.data.network.trailers

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.response.Videos

interface TrailerNetworkDataSource {
    val downloadedTrailers: LiveData<Videos>

    suspend fun fetchMovieTrailers(
        movieId: String
    )
}