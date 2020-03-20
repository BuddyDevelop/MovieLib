package bary.apps.moviesLib.ui.movies.newest

import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred

const val VOTE_COUNT_1000 = "1000"
const val SORT_BY_RELEASE_DATE_DESC = "release_date.desc"
const val LANGUAGE_CODE_EN = "en"

class NewestMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val newestMovies by lazyDeferred {
        moviesRepository.fetchMovies(VOTE_COUNT_1000, SORT_BY_RELEASE_DATE_DESC, LANGUAGE_CODE_EN)
    }
}
