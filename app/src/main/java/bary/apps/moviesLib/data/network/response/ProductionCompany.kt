package bary.apps.moviesLib.data.network.response


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    val id: Int, // 420
    @SerializedName("logo_path")
    val logoPath: String, // /hUzeosd33nzE5MCNsZxCGEKTXaQ.png
    val name: String, // Marvel Studios
    @SerializedName("origin_country")
    val originCountry: String // US
)