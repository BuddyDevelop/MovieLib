package bary.apps.moviesLib.ui.movies.details

import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.base.BaseMoviesViewModel

class MovieDetailViewModel(
    override val moviesRepository: MoviesRepository,
    private val movieId: String
) : BaseMoviesViewModel(moviesRepository) {

    val movieVideos by lazyDeferred {
        moviesRepository.getMovieTrailers(movieId)
    }

    val movieReviews by lazyDeferred {
        moviesRepository.getMovieReviews(movieId)
    }

    val similarMovies by lazyDeferred {
        moviesRepository.getSimilarMovies(movieId)
    }
}