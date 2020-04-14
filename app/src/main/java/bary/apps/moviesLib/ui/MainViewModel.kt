package bary.apps.moviesLib.ui

import androidx.lifecycle.*
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred

class MainViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val favouriteMoviesCount by lazyDeferred {
        moviesRepository.getFavouriteMoviesCount()
    }

    val watchlistMovieCount by lazyDeferred {
        moviesRepository.getWatchlistMoviesCount()
    }

}