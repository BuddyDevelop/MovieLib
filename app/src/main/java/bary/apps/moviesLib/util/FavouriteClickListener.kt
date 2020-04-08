package bary.apps.moviesLib.util

import android.view.View
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import com.shashank.sony.fancytoastlib.FancyToast

interface FavouriteClickListener {
    val moviesRepository: MoviesRepository

    fun favouriteBtnClick(view: View, movie: Movie){
        movie.isFavourite = true
        moviesRepository.updateMovie(movie)
        val addedToFavouriteMsg: String = view.context.getString(R.string.added_to_favourite, movie.title)
        FancyToast.makeText(view.context, addedToFavouriteMsg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }
}