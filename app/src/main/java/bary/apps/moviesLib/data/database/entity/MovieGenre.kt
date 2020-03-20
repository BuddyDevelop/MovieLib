package bary.apps.moviesLib.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//, foreignKeys = arrayOf(
//ForeignKey(
//entity = Movie::class,
//parentColumns = arrayOf("id"),
//childColumns = arrayOf("movie_id")
//) )
@Entity(tableName = "movie_genre" )
data class MovieGenre(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var movie_id: Int,
    var genre_id: Int
)