package bary.apps.moviesLib.data.network.response


import bary.apps.moviesLib.data.database.entity.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    var id: Int,
    val page: Int, // 1
    @SerializedName("total_results")
    val totalResults: Int, // 3943
    @SerializedName("total_pages")
    val totalPages: Int, // 198
    @SerializedName("results")
    val movies: List<Movie>
)