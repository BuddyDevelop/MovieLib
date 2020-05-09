package bary.apps.moviesLib.ui.movies.topRated

import androidx.lifecycle.viewModelScope
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.base.BaseMoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopRatedMoviesViewModel(
    moviesRepository: MoviesRepository
) : BaseMoviesViewModel(moviesRepository) {
    //get top rated movies
    suspend fun fetchPageOfTopRatedMovies(page: Int) = lazyDeferred{
        moviesRepository.getTopRatedMovies(page)
    }

    fun getNextPageOfTopRatedMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            fetchNextPageOfTopRatedMovies(page)
        }
    }

    private suspend fun fetchNextPageOfTopRatedMovies(page: Int) {
        moviesRepository.getTopRatedMovies(page).value
    }
}