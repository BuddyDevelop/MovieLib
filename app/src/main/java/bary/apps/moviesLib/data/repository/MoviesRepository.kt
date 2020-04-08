package bary.apps.moviesLib.data.repository

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Videos

interface MoviesRepository {
    suspend fun getMovie(movieId: String): LiveData<MovieDetails>
    suspend fun getMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse>
    suspend fun getMostPopularMovies() : LiveData<MoviesResponse>
    suspend fun getTopRatedMovies() : LiveData<MoviesResponse>
    suspend fun getMovieTrailers(movieId: String): LiveData<Videos>
    suspend fun getSearchedMovies(movieName: String) : LiveData<MoviesResponse>
    fun updateMovie(movie: Movie)
}