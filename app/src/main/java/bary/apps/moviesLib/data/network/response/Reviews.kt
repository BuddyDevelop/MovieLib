package bary.apps.moviesLib.data.network.response

import com.google.gson.annotations.SerializedName

data class Reviews(
	val id: Int,
	val page: Int,
	@SerializedName("total_pages")
	val totalPages: Int,
	@SerializedName("results")
	val reviews: List<Review>,
	@SerializedName("total_results")
	val totalResults: Int
)