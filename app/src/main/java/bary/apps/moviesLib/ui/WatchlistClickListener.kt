package bary.apps.moviesLib.ui

import android.view.View
import android.widget.Toast
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository

interface WatchlistClickListener {
    val moviesRepository: MoviesRepository

    fun watchlistBtnClick(view: View, movie: Movie){
        movie.isWatchlist = true
        moviesRepository.updateMovie(movie)
        val addedToFavouriteMsg: String = view.context.getString(R.string.added_to_watchlist, movie.title)
        Toast.makeText(view.context, addedToFavouriteMsg, Toast.LENGTH_LONG).show()
    }
}