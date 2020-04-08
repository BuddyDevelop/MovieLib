package bary.apps.moviesLib.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import bary.apps.moviesLib.util.FavouriteClickListener
import bary.apps.moviesLib.util.WatchlistClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val VOTE_COUNT_50 = "50"
const val SORT_BY_RELEASE_DATE_DESC = "primary_release_date.desc"
const val LANGUAGE_CODE_EN = "en"

class BaseMoviesViewModel(
    override val moviesRepository: MoviesRepository
) : ViewModel(), FavouriteClickListener,
    WatchlistClickListener {

    val newestMovies by lazyDeferred {
        moviesRepository.getMovies(
            VOTE_COUNT_50,
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

    private val _searchedMovies = MutableLiveData<MoviesResponse>()
    val searchedMovies: LiveData<MoviesResponse>
        get() = _searchedMovies

    fun searchMovies(movieName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                fetchMovies(movieName)
            }
        }
    }

    private suspend fun fetchMovies(movieName: String) {
        _searchedMovies.postValue(moviesRepository.getSearchedMovies(movieName).value)
    }
}
