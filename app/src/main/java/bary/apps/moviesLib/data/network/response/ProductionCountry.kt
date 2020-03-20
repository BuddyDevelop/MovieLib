package bary.apps.moviesLib.data.network.response


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String, // US
    val name: String // United States of America
)