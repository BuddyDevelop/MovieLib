package bary.apps.moviesLib.data.network.trailers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.Videos
import bary.apps.moviesLib.internal.NoConnectivityException

class TrailerNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : TrailerNetworkDataSource {
    private val _downloadedTrailers = MutableLiveData<Videos>()
    override val downloadedTrailers: LiveData<Videos>
        get() = _downloadedTrailers

    override suspend fun fetchMovieTrailers(movieId: String) {
        try {
            val fetchMovieTrailers = tmdbApiService
                .getMovieVideos(movieId)
                .await()
            _downloadedTrailers.postValue(fetchMovieTrailers)
        }
        catch (e: NoConnectivityException){
            Log.e("MovieTrailers", "No internet connection.", e)
        }
    }
}