package bary.apps.moviesLib.ui.movies.details

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import bary.apps.moviesLib.data.BASE_POSTER_PATH
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.internal.lazyDeferred
import com.bumptech.glide.Glide

class MovieDetailViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val movies by lazyDeferred {
        moviesRepository.getMovie()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty())
                Glide.with(view.context).load(BASE_POSTER_PATH + url).into(view)

        }
    }
}