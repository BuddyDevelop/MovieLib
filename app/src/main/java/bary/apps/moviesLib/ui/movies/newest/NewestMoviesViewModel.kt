package bary.apps.moviesLib.ui.movies.newest

import androidx.lifecycle.viewModelScope
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.base.BaseMoviesViewModel
import bary.apps.moviesLib.ui.base.LANGUAGE_CODE_EN
import bary.apps.moviesLib.ui.base.SORT_BY_RELEASE_DATE_DESC
import bary.apps.moviesLib.ui.base.VOTE_COUNT_20
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewestMoviesViewModel(
    moviesRepository: MoviesRepository
) : BaseMoviesViewModel(moviesRepository){
    //get newest movies
    suspend fun fetchPageOfNewestMovies(page: Int) = lazyDeferred{
        moviesRepository.getMovies(
            VOTE_COUNT_20,
            SORT_BY_RELEASE_DATE_DESC,
            LANGUAGE_CODE_EN,
            page
        )
    }

    fun getNextPageOfNewestMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            fetchNextPageOfNewestMovies(page)
        }
    }

    private suspend fun fetchNextPageOfNewestMovies(page: Int) {
        moviesRepository.getMovies(
            VOTE_COUNT_20,
            SORT_BY_RELEASE_DATE_DESC,
            LANGUAGE_CODE_EN,
            page
        ).value
    }
}