package bary.apps.moviesLib.data.network.response


import com.google.gson.annotations.SerializedName

data class Videos(
    val id: Int, // 454626
    @SerializedName("results")
    val videos: List<Video>
)