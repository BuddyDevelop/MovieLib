package bary.apps.moviesLib.util

import android.view.View
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import com.shashank.sony.fancytoastlib.FancyToast

interface WatchlistClickListener {
    val moviesRepository: MoviesRepository

    fun watchlistBtnClick(view: View, movie: Movie){
        movie.isWatchlist = true
        moviesRepository.updateMovie(movie)
        val addedToFavouriteMsg: String = view.context.getString(R.string.added_to_watchlist, movie.title)
        FancyToast.makeText(view.context, addedToFavouriteMsg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }
}