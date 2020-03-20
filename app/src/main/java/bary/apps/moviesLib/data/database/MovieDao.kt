package bary.apps.moviesLib.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bary.apps.moviesLib.data.database.entity.MovieDetails

@Dao
interface MovieDao {
    //update or insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(movie: MovieDetails)

    @Query("select * from movies where id = :movieId")
    fun getMovie(movieId: String): LiveData<MovieDetails>

    @Query("delete from movies where id = :movieId")
    fun deleteMovie(movieId: String)
}