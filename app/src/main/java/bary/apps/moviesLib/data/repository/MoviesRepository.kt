package bary.apps.moviesLib.data.repository

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.response.MoviesResponse

interface MoviesRepository {
    suspend fun getMovie(): LiveData<MovieDetails>
    suspend fun fetchMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse>
}