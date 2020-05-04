package bary.apps.moviesLib.data.network.newestMovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.internal.NoConnectivityException

class NewestMoviesNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : NewestMoviesNetworkDataSource {
    private val _downloadedNewestMovies = MutableLiveData<MoviesResponse>()
    override val downloadedNewestMovies: LiveData<MoviesResponse>
        get() = _downloadedNewestMovies

    override suspend fun fetchMovies(voteCount: String, sortBy: String, languageCode: String, page: Int) {
        try {
            val fetchMovies = tmdbApiService
                .getMoviesByVoteCountAndSortByRelaseDate(voteCount, sortBy, languageCode, page)
                .await()
            _downloadedNewestMovies.postValue(fetchMovies)
        }
        catch (e: NoConnectivityException){
            Log.e("NewestMovies", "No internet connection.", e)
        }
    }
}