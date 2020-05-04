package bary.apps.moviesLib.ui.movies.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bary.apps.moviesLib.data.repository.MoviesRepository

class PopularMoviesViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularMoviesViewModel(moviesRepository) as T
    }
}