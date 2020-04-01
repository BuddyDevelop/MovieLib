package bary.apps.moviesLib.data.network.topRatedMovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.internal.NoConnectivityException

class TopRatedMoviesNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : TopRatedMoviesNetworkDataSource {
    private val _downloadedTopRatedMovies = MutableLiveData<MoviesResponse>()
    override val downloadedTopRatedMovies: LiveData<MoviesResponse>
        get() = _downloadedTopRatedMovies

    override suspend fun fetchTopRatedMovies() {
        try {
            val fetchedMovies = tmdbApiService
                .getTopRatedMovies()
                .await()

            _downloadedTopRatedMovies.postValue(fetchedMovies)
        }
        catch (e: NoConnectivityException){
            Log.e("TopRated", "No internet connection.", e)
        }
    }
}