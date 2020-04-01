package bary.apps.moviesLib.ui.movies

import bary.apps.moviesLib.data.network.response.MoviesResponse

interface MovieToMovieItemConverter {
    val viewModel: BaseMoviesViewModel

    fun MoviesResponse.toMoviesEntries(): List<MovieItem> {
        return this.movies.map {
            MovieItem(it, viewModel)
        }
    }
}