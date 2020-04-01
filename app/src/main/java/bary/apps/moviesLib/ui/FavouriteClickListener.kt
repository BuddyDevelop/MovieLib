package bary.apps.moviesLib.ui

import android.view.View
import bary.apps.moviesLib.data.database.entity.Movie

interface FavouriteClickListener {
    fun favouriteBtnClick(view: View, movie: Movie)
}