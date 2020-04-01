package bary.apps.moviesLib.ui.movies.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred

class MovieDetailViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieId: String
) : ViewModel() {

    private val movie = MutableLiveData<Movie>()

    val forwardedMovie = movie

    fun setForwardedMovie(forwardedMovie: Movie){
        movie.value = forwardedMovie
    }

    val movieVideos by lazyDeferred {
        moviesRepository.getMovieTrailers(movieId)
    }
}