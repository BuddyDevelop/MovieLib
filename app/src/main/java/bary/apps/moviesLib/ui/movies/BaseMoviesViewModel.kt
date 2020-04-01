package bary.apps.moviesLib.ui.movies

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.ui.FavouriteClickListener

const val VOTE_COUNT_1000 = "1000"
const val SORT_BY_RELEASE_DATE_DESC = "release_date.desc"
const val LANGUAGE_CODE_EN = "en"

class BaseMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(), FavouriteClickListener {

    val newestMovies by lazyDeferred {
        moviesRepository.getMovies(
            VOTE_COUNT_1000,
            SORT_BY_RELEASE_DATE_DESC,
            LANGUAGE_CODE_EN
        )
    }

    val popularMovies by lazyDeferred {
        moviesRepository.getMostPopularMovies()
    }

    val topRatedMovies by lazyDeferred {
        moviesRepository.getTopRatedMovies()
    }

    override fun favouriteBtnClick(view: View, movie: Movie) {
        movie.isFavourite = true
        moviesRepository.updateMovie(movie)
        val addedToFavouriteMsg: String = view.context.getString(R.string.added_to_favourite, movie.title)
        Toast.makeText(view.context, addedToFavouriteMsg, Toast.LENGTH_LONG).show()
    }
}
