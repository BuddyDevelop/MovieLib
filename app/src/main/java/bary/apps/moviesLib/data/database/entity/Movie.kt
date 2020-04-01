package bary.apps.moviesLib.data.database.entity


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int, // 290859
    val popularity: Double, // 64.094
    val video: Boolean, // false
    @SerializedName("poster_path")
    val posterPath: String, // /vqzNJRH4YyquRiWxCCOH0aXggHI.jpg
    @SerializedName("backdrop_path")
    val backdropPath: String, // /riTANvQ8GKmQbgtC1ps3OfkU43A.jpg
    @SerializedName("original_language")
    val originalLanguage: String, // en
    @SerializedName("original_title")
    val originalTitle: String, // Terminator: Dark Fate
//    @SerializedName("genre_ids")
//    val genreIds: List<Int>,
    val title: String, // Terminator: Dark Fate
    @SerializedName("vote_average")
    val voteAverage: Double, // 6.3
    @SerializedName("vote_count")
    val voteCount: Int, // 1647
    val overview: String, // Decades after Sarah Connor prevented Judgment Day, a lethal new Terminator is sent to eliminate the future leader of the resistance. In a fight to save mankind, battle-hardened Sarah Connor teams up with an unexpected ally and an enhanced super soldier to stop the deadliest Terminator yet.
    @SerializedName("release_date")
    val releaseDate: String, // 2019-10-23
    var isFavourite: Boolean?, //local db check if user likes movie
    var isWatchlist: Boolean?,//local db check if user would like to watch it
    var watchedCount: Boolean?
) : Parcelable