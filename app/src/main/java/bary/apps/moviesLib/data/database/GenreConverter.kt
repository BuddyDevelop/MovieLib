package bary.apps.moviesLib.data.database

import androidx.room.TypeConverter
import bary.apps.moviesLib.data.database.entity.MovieGenre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverter {
    @TypeConverter
    fun getMovieGenresIds(genreIds: List<Int>): String {
        val gson = Gson()
        val type = object : TypeToken<List<MovieGenre>>(){}.type
        return gson.toJson( genreIds, type)
    }

    @TypeConverter
    fun setMovieGenresId(genreId: Int): List<MovieGenre>{
        val gson = Gson()
        val type = object : TypeToken<List<MovieGenre>>() {}.type
        return gson.fromJson(genreId.toString(), type)
    }
}