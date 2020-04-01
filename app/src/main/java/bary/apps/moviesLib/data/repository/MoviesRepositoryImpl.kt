package bary.apps.moviesLib.data.repository

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.MovieDao
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSource
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.popularMovies.PopularMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Videos
import bary.apps.moviesLib.data.network.topRatedMovies.TopRatedMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.trailers.TrailerNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource,
    private val newestMoviesNetworkDataSource: NewestMoviesNetworkDataSource,
    private val movieTrailerNetworkDataSource: TrailerNetworkDataSource,
    private val popularMoviesNetworkDataSource: PopularMoviesNetworkDataSource,
    private val topRatedMoviesNetworkDataSource: TopRatedMoviesNetworkDataSource
) : MoviesRepository {

    override fun updateMovie(movie: Movie){
        GlobalScope.launch(Dispatchers.IO){
            movieDao.updateMovie(movie)
        }
    }

    //get movie details
    override suspend fun getMovie(movieId: String): LiveData<MovieDetails> {
        return withContext(Dispatchers.IO){
            fetchMovieDetails(movieId)
            return@withContext(movieDetailsNetworkDataSource.downloadedMovieDetails)
        }
    }

    private suspend fun fetchMovieDetails(movieId: String){
        movieDetailsNetworkDataSource.fetchMovieById(movieId)
    }
    //end movies details

    //get list of movies
    override suspend fun getMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            fetchNewestMovies(voteCount, sortBy, languageCode)
            return@withContext(newestMoviesNetworkDataSource.downloadedNewestMovies)
        }
    }

    private suspend fun fetchNewestMovies(voteCount: String, sortBy: String, languageCode: String) {
        newestMoviesNetworkDataSource.fetchMovies(voteCount, sortBy, languageCode)
    }
    //end movie listing

    //get movie trailer
    override suspend fun getMovieTrailers(movieId: String): LiveData<Videos> {
        return withContext(Dispatchers.IO){
            fetchMovieTrailers(movieId)
            return@withContext(movieTrailerNetworkDataSource.downloadedTrailers)
        }
    }

    private suspend fun fetchMovieTrailers(movieId: String) {
        movieTrailerNetworkDataSource.fetchMovieTrailers(movieId)
    }
    //end movie trailer

    //get popular movies
    override suspend fun getMostPopularMovies(): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO){
            fetchPopularMovies()
            return@withContext(popularMoviesNetworkDataSource.downloadedPopularMovies)
        }
    }

    private suspend fun fetchPopularMovies(){
        popularMoviesNetworkDataSource.fetchPopularMovies()
    }
    //end popular movies

    //get top rated movies
    override suspend fun getTopRatedMovies(): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO){
            fetchTopRatedMovies()
            return@withContext(topRatedMoviesNetworkDataSource.downloadedTopRatedMovies)
        }
    }

    private suspend fun fetchTopRatedMovies(){
        topRatedMoviesNetworkDataSource.fetchTopRatedMovies()
    }
    //end top rated movies
}