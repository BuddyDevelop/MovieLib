package bary.apps.moviesLib.ui.movies.details

import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.FavouriteClickListener
import bary.apps.moviesLib.ui.WatchlistClickListener

class MovieDetailViewModel(
    override val moviesRepository: MoviesRepository,
    private val movieId: String
) : ViewModel(), FavouriteClickListener, WatchlistClickListener {

    val movieVideos by lazyDeferred {
        moviesRepository.getMovieTrailers(movieId)
    }

}