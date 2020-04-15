package bary.apps.moviesLib.util

import android.view.View
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.item_movie.view.*

interface WatchlistClickListener : RemovalAlertDialog {
    val moviesRepository: MoviesRepository

    fun watchlistBtnClick(view: View, movie: Movie) {
        val addBtn = view.add_watchlist_btn

        if (addBtn != null && addBtn.tag?.toString() == REMOVE_ACTION_TAG) { //remove watchlist movie from database
            val dialogTitle = view.context.getString(R.string.remove_watchlist_title_msg)
            val dialogMsg = view.context.getString(R.string.remove_watchlist_msg)
            val removedFromWatchlistMsg: String =
                view.context.getString(R.string.removed_from_watchlist, movie.title)

            alertDialog(view.context, dialogTitle, dialogMsg, removedFromWatchlistMsg, movie.id)
            { moviesRepository.watchlistMovieRemoveOrUpdate(movie.id) }
        }
        else {  //add watchlist movie to database
            val addedToWatchlistMsg: String =
                view.context.getString(R.string.added_to_watchlist, movie.title)

            movie.isWatchlist = true
            moviesRepository.watchlistMovieInsertOrUpdate(movie.id, movie)

            FancyToast.makeText(view.context, addedToWatchlistMsg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
        }
    }
}