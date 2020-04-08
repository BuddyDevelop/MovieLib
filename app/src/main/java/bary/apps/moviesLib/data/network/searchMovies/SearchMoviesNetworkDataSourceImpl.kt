package bary.apps.moviesLib.data.network.searchMovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.internal.NoConnectivityException

class SearchMoviesNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : SearchMoviesNetworkDataSource {
    private val _downloadedMovies = MutableLiveData<MoviesResponse>()
    override val downloadedMovies: LiveData<MoviesResponse>
        get() = _downloadedMovies

    override suspend fun fetchMoviesByName(movieName: String) {
        try {
            val fetchedMovies = tmdbApiService
                .searchMovies(movieName)
                .await()

            _downloadedMovies.postValue(fetchedMovies)
        }
        catch (e: NoConnectivityException){
            Log.e("SearchMovies", "No internet connection.", e)
        }

    }
}