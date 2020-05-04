package bary.apps.moviesLib.data.network.response

data class Review(
	val author: String,
	val id: String,
	val content: String,
	val url: String? = null
)