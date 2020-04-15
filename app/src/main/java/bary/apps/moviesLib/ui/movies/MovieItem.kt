package bary.apps.moviesLib.ui.movies

import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.databinding.ItemMovieBindingImpl
import com.xwray.groupie.databinding.BindableItem

open class MovieItem(
    open val movieItem: Movie,
    open val viewModel: BaseMoviesViewModel
) : BindableItem<ItemMovieBindingImpl>() {
    override fun getLayout() = R.layout.item_movie

    override fun bind(binding: ItemMovieBindingImpl, position: Int) {
        binding.movie = movieItem
        binding.viewModel = viewModel
    }
}