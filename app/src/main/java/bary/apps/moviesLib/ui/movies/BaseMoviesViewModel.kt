package bary.apps.moviesLib.ui.movies

import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.FavouriteClickListener
import bary.apps.moviesLib.ui.WatchlistClickListener

const val VOTE_COUNT_1000 = "1000"
const val SORT_BY_RELEASE_DATE_DESC = "release_date.desc"
const val LANGUAGE_CODE_EN = "en"

class BaseMoviesViewModel(
    override val moviesRepository: MoviesRepository
) : ViewModel(), FavouriteClickListener, WatchlistClickListener {

    val newestMovies by lazyDeferred {
        moviesRepository.getMovies(
            VOTE_COUNT_1000,
            SORT_BY_RELEASE_DATE_DESC,
            LANGUAGE_CODE_EN
        )
    }

    val popularMovies by lazyDeferred {
        moviesRepository.getMostPopularMovies()
    }

    val topRatedMovies by lazyDeferred {
        moviesRepository.getTopRatedMovies()
    }
}
