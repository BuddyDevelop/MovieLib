package bary.apps.moviesLib.data.database.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieDetails(
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = false)
    val id: Int, // 447365
    @ColumnInfo(name="adult")
    val adult: Boolean, // false
    @ColumnInfo(name="backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String, // /https://image.tmdb.org/t/p/w780.jpg
    @ColumnInfo(name="budget")
    val budget: Double, // 0
    @ColumnInfo(name="homepage")
    val homepage: String,
    @ColumnInfo(name="imdb_id")
    @SerializedName("imdb_id")
    val imdbId: String, // tt6791350
    @ColumnInfo(name="original_language")
    @SerializedName("original_language")
    val originalLanguage: String, // en
    @ColumnInfo(name="original_title")
    @SerializedName("original_title")
    val originalTitle: String, // Guardians of the Galaxy Vol. 3
    @ColumnInfo(name="overwiev")
    val overview: String, // The third film based on Marvel's Guardians of the Galaxy.
    @ColumnInfo(name="popularity")
    val popularity: Double, // 8.821
    @ColumnInfo(name="poster_path")
    @SerializedName("poster_path")
    val posterPath: String, // /6rsLfXqlZSXa6zLfbofHVhXs8Yr.jpg
    @ColumnInfo(name="release_date")
    @SerializedName("release_date")
    val releaseDate: String, // 2023-02-16
    @ColumnInfo(name="revenue")
    val revenue: Int, // 0
    @ColumnInfo(name="runtime")
    val runtime: Double, // 0
    @ColumnInfo(name="status")
    val status: String, // Planned
    @ColumnInfo(name="tagline")
    val tagline: String,
    @ColumnInfo(name="title")
    val title: String, // Guardians of the Galaxy Vol. 3
    @ColumnInfo(name="vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double, // 0.0
    @ColumnInfo(name="vote_count")
    @SerializedName("vote_count")
    val voteCount: Int // 0
)