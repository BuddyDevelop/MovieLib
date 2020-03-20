package bary.apps.moviesLib.data.network.movieDetails

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.entity.MovieDetails

interface MovieDetailsNetworkDataSource {
    val downloadedMovieDetails: LiveData<MovieDetails>

    suspend fun fetchMovieById(
        movieId: String
    )
}