package bary.apps.moviesLib.data.network.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bary.apps.moviesLib.data.network.TmdbApiService
import bary.apps.moviesLib.data.network.response.Reviews
import bary.apps.moviesLib.internal.NoConnectivityException

class MovieReviewsNetworkDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : MovieReviewsNetworkDataSource {
    private val _downloadedReviews = MutableLiveData<Reviews>()
    override val downloadedReviews: LiveData<Reviews>
        get() = _downloadedReviews

    override suspend fun getReviewByMovieId(id: String) {
        try {
            val reviews = tmdbApiService
                .getReviews(id)
                .await()

            _downloadedReviews.postValue(reviews)
        }
        catch (e: NoConnectivityException){
            Log.e("Reviews", "No internet connection.", e)
        }
    }
}