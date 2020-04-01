package bary.apps.moviesLib.ui

import android.view.View
import android.widget.Toast
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository

interface FavouriteClickListener {
    val moviesRepository: MoviesRepository

    fun favouriteBtnClick(view: View, movie: Movie){
        movie.isFavourite = true
        moviesRepository.updateMovie(movie)
        val addedToFavouriteMsg: String = view.context.getString(R.string.added_to_favourite, movie.title)
        Toast.makeText(view.context, addedToFavouriteMsg, Toast.LENGTH_LONG).show()
    }
}