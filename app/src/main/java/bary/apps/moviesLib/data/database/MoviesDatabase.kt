package bary.apps.moviesLib.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bary.apps.moviesLib.data.database.entity.MovieDetails

@Database(
    entities = [MovieDetails::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MoviesDatabase::class.java, "movies.db")
                .build()

    }
}