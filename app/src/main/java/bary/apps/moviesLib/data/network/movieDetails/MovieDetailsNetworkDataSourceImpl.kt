package bary.apps.moviesLib.data.network.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.internal.NoConnectivityException

class MovieDetailsNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : MovieDetailsNetworkDataSource {
    private val _downloadedMovieDetails = MutableLiveData<MovieDetails>()
    override val downloadedMovieDetails: LiveData<MovieDetails>
        get() = _downloadedMovieDetails

    override suspend fun fetchMovieById(movieId: String) {
        try {
            val fetchMovieDetails = tmdbApiService
                .getMovieById(movieId)
                .await()
            _downloadedMovieDetails.postValue(fetchMovieDetails)
        }
        catch (e: NoConnectivityException){
            Log.e("MovieDetails", "No internet connection.", e)
        }
    }
}