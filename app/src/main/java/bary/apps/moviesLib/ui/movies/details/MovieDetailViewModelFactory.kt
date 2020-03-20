package bary.apps.moviesLib.ui.movies.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bary.apps.moviesLib.data.repository.MoviesRepository

class MovieDetailViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(moviesRepository) as T
    }
}