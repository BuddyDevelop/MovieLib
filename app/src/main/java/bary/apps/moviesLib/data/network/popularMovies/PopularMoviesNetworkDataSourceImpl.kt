package bary.apps.moviesLib.data.network.popularMovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.internal.NoConnectivityException

class PopularMoviesNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : PopularMoviesNetworkDataSource {
    private val _downloadedPopularMovies = MutableLiveData<MoviesResponse>()
    override val downloadedPopularMovies: LiveData<MoviesResponse>
        get() = _downloadedPopularMovies

    override suspend fun fetchPopularMovies() {
        try {
            val fetchMovies = tmdbApiService
                .getPopularMovies()
                .await()

            _downloadedPopularMovies.postValue(fetchMovies)
        }
        catch (e: NoConnectivityException){
            Log.e("PopularMovies", "No internet connection.", e)
        }

    }
}