package bary.apps.moviesLib.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bary.apps.moviesLib.data.database.entity.Movie

@Dao
interface MovieDao {
    //update or insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)

    @Query("select * from movies where id = :movieId")
    fun getMovie(movieId: String): LiveData<Movie>

    @Query("delete from movies where id = :movieId")
    fun deleteMovie(movieId: String)
}