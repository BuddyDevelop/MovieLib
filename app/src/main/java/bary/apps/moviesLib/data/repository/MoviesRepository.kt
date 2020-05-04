package bary.apps.moviesLib.data.repository

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Videos

interface MoviesRepository {
    suspend fun getMovie(movieId: String): LiveData<MovieDetails>
    suspend fun getMovies(voteCount: String, sortBy: String, languageCode: String, page: Int): LiveData<MoviesResponse>
    suspend fun getMostPopularMovies(page: Int) : LiveData<MoviesResponse>
    suspend fun getTopRatedMovies(page: Int) : LiveData<MoviesResponse>
    suspend fun getMovieTrailers(movieId: String): LiveData<Videos>
    suspend fun getSearchedMovies(movieName: String) : LiveData<MoviesResponse>
    fun updateMovie(movie: Movie)
    fun deleteMovie(id: Int)
    fun favMovieInsertOrUpdate(id: Int, movie: Movie)
    fun favMovieRemoveOrUpdate(id: Int)
    fun watchlistMovieInsertOrUpdate(id: Int, movie: Movie)
    fun watchlistMovieRemoveOrUpdate(id: Int)
    suspend fun getFavouriteMovies() : LiveData<List<Movie>>
    suspend fun getWatchlistMovies() : LiveData<List<Movie>>
    suspend fun getFavouriteMoviesCount() : LiveData<Int>
    suspend fun getWatchlistMoviesCount() : LiveData<Int>
}