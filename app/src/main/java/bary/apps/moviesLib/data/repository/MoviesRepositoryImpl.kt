package bary.apps.moviesLib.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.MovieDao
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSource
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Videos
import bary.apps.moviesLib.data.network.trailers.TrailerNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime


class MoviesRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource,
    private val newestMoviesNetworkDataSource: NewestMoviesNetworkDataSource,
    private val movieTrailerNetworkDataSource: TrailerNetworkDataSource
) : MoviesRepository {

    //get movies details
    init {
        movieDetailsNetworkDataSource.downloadedMovieDetails.observeForever {
                newMovies -> persistFetchedMovieDetails(newMovies)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun persistFetchedMovieDetails(fetchedMovie: MovieDetails){
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.upsert(fetchedMovie)
        }
    }

    override suspend fun getMovie(movieId: String): LiveData<MovieDetails> {
        return withContext(Dispatchers.IO){
            initMoviesData(movieId)
            return@withContext(movieDao.getMovie(movieId))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initMoviesData(movieId: String){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchMovieDetails(movieId)
        }
    }

    private suspend fun fetchMovieDetails(movieId: String){
        movieDetailsNetworkDataSource.fetchMovieById(movieId)
    }
    //end movies details

    //get list of movies
    override suspend fun fetchMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            fetchNewestMovies(voteCount, sortBy, languageCode)
            return@withContext(newestMoviesNetworkDataSource.downloadedNewestMovies)
        }
    }

    private suspend fun fetchNewestMovies(voteCount: String, sortBy: String, languageCode: String) {
        newestMoviesNetworkDataSource.fetchMovies(voteCount, sortBy, languageCode)
    }
    //end movie listing

    override suspend fun getMovieTrailers(movieId: String): LiveData<Videos> {
        return withContext(Dispatchers.IO){
            fetchMovieTrailers(movieId)
            return@withContext(movieTrailerNetworkDataSource.downloadedTrailers)
        }
    }

    private suspend fun fetchMovieTrailers(movieId: String) {
        movieTrailerNetworkDataSource.fetchMovieTrailers(movieId)
    }
}