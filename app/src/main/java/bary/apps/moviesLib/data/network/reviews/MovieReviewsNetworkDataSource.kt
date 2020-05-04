package bary.apps.moviesLib.data.network.reviews

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.network.response.Reviews

interface MovieReviewsNetworkDataSource {
    val downloadedReviews: LiveData<Reviews>

    suspend fun getReviewByMovieId(id: String)
}