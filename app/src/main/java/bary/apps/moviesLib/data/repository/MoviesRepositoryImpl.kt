package bary.apps.moviesLib.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.MovieDao
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSource
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.response.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime


class MoviesRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource,
    private val newestMoviesNetworkDataSource: NewestMoviesNetworkDataSource
) : MoviesRepository {

    init {
        movieDetailsNetworkDataSource.downloadedMovieDetails.observeForever {
                newMovies -> persistFetchedMovieDetails(newMovies)
        }
    }

    private fun persistFetchedMovieDetails(fetchedMovies: MovieDetails){
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.upsert(fetchedMovies)
        }
    }

    override suspend fun getMovie(): LiveData<MovieDetails> {
        return withContext(Dispatchers.IO){
            initMoviesData()
            return@withContext(movieDao.getMovie("290859"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initMoviesData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchMovieDetails()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchMovieDetails(){
        movieDetailsNetworkDataSource.fetchMovieById("290859")
    }

    override suspend fun fetchMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            fetchNewestMovies(voteCount, sortBy, languageCode)
            return@withContext(newestMoviesNetworkDataSource.downloadedNewestMovies)
        }
    }

    private suspend fun fetchNewestMovies(voteCount: String, sortBy: String, languageCode: String) {
        newestMoviesNetworkDataSource.fetchMovies(voteCount, sortBy, languageCode)
    }

}