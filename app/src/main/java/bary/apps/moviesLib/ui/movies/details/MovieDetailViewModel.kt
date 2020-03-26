package bary.apps.moviesLib.ui.movies.details

import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred

class MovieDetailViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieId: String
) : ViewModel() {

    val movie by lazyDeferred {
        moviesRepository.getMovie(movieId)
    }

    val movieVideos by lazyDeferred {
        moviesRepository.getMovieTrailers(movieId)
    }
}