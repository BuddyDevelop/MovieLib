package bary.apps.moviesLib.ui.base

import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.util.FavouriteClickListener
import bary.apps.moviesLib.util.WatchlistClickListener

const val VOTE_COUNT_20 = "20"
const val SORT_BY_RELEASE_DATE_DESC = "primary_release_date.desc"
const val LANGUAGE_CODE_EN = "en"

open class BaseMoviesViewModel(
    override val moviesRepository: MoviesRepository
) : ViewModel(), FavouriteClickListener,
    WatchlistClickListener {

    val favouriteMovies by lazyDeferred {
        moviesRepository.getFavouriteMovies()
    }

    val watchlistMovies by lazyDeferred {
        moviesRepository.getWatchlistMovies()
    }

    //search movies
    suspend fun observeSearchMovies(movieName: String) = lazyDeferred {
        moviesRepository.getSearchedMovies(movieName)
    }
}
