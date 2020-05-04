package bary.apps.moviesLib.ui.movies.topRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bary.apps.moviesLib.data.repository.MoviesRepository

class TopRatedMoviesViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopRatedMoviesViewModel(moviesRepository) as T
    }
}