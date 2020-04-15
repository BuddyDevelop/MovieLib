package bary.apps.moviesLib.util

import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModel
import bary.apps.moviesLib.ui.movies.MovieItem

interface MovieToMovieItemConverter {
    val viewModel: BaseMoviesViewModel

    fun toMoviesEntries(movies: List<Movie>): List<MovieItem> {
        return movies.map {
            MovieItem(it, viewModel)
        }
    }
}