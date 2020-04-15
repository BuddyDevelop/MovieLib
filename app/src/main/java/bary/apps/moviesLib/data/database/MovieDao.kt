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

    @Query("update movies set isFavourite = :isFavourite where id = :id")
    fun setFavouriteMovie(id: Int, isFavourite: Boolean = true)

    @Query("update movies set isWatchlist = :isWatchlist where id = :id")
    fun setWatchlistMovie(id: Int, isWatchlist: Boolean = true)

    @Query("update movies set isFavourite = :isFavourite where id = :id")
    fun removeFavouriteMovie(id: Int, isFavourite: Boolean = false)

    @Query("update movies set isWatchlist = :isWatchlist where id = :id")
    fun removeWatchlistMovie(id: Int, isWatchlist: Boolean = false)

    @Query("select * from movies where id = :movieId limit 1")
    fun getMovie(movieId: Int): Movie?

    @Query("select * from movies where isFavourite = :isFavourite")
    fun getFavouriteMovies(isFavourite: Boolean = true): LiveData<List<Movie>>

    @Query("select * from movies where isWatchlist = :isWatchlist")
    fun getWatchlistMovies(isWatchlist: Boolean = true): LiveData<List<Movie>>

    @Query("select count(id) from movies where isFavourite = :isFavourite")
    fun getFavouriteMoviesCount(isFavourite: Boolean = true) : LiveData<Int>

    @Query("select count(id) from movies where isWatchlist = :isWatchlist")
    fun getWatchlistMoviesCount(isWatchlist: Boolean = true) : LiveData<Int>

    @Query("delete from movies where id = :movieId")
    fun deleteMovie(movieId: Int)
}