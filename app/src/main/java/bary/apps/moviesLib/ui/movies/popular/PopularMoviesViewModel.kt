package bary.apps.moviesLib.ui.movies.popular

import androidx.lifecycle.viewModelScope
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.base.BaseMoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    moviesRepository: MoviesRepository
) : BaseMoviesViewModel(moviesRepository) {
    //get popular movies
    suspend fun fetchPageOfPopularMovies(page: Int) = lazyDeferred{
        moviesRepository.getMostPopularMovies(page)
    }

    fun getNextPageOfPopularMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            fetchNextPageOfPopularMovies(page)
        }
    }

    private suspend fun fetchNextPageOfPopularMovies(page: Int) {
        moviesRepository.getMostPopularMovies(page).value
    }

}