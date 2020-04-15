package bary.apps.moviesLib.util

import android.view.View
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.item_movie.view.*

const val REMOVE_ACTION_TAG = "remove"

interface FavouriteClickListener : RemovalAlertDialog {
    val moviesRepository: MoviesRepository

    fun favouriteBtnClick(view: View, movie: Movie){
        val heartBtn = view.heart_fav_button

        if (heartBtn != null && heartBtn.tag?.toString() == REMOVE_ACTION_TAG){ //remove favourite movie from database
            val dialogTitle = view.context.getString(R.string.remove_favourites_title_msg)
            val dialogMsg = view.context.getString(R.string.remove_favourites_msg)
            val removedFromFavouriteMsg: String =
               view.context.getString(R.string.removed_from_favourite, movie.title)

            alertDialog(view.context, dialogTitle, dialogMsg, removedFromFavouriteMsg, movie.id)
            { moviesRepository.favMovieRemoveOrUpdate(movie.id) }
        }
        else { //add favourite movie to database
            val addedToFavouriteMsg: String =
                view.context.getString(R.string.added_to_favourite, movie.title)

            movie.isFavourite = true
            moviesRepository.favMovieInsertOrUpdate(movie.id, movie)

            FancyToast.makeText(view.context, addedToFavouriteMsg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
        }
    }
}