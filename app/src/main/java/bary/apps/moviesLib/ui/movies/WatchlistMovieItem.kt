package bary.apps.moviesLib.ui.movies

import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.databinding.ItemMovieBindingImpl
import bary.apps.moviesLib.util.REMOVE_ACTION_TAG
import kotlinx.android.synthetic.main.item_movie.view.*

class WatchlistMovieItem(
    override val movieItem: Movie,
    override val viewModel: BaseMoviesViewModel
) : MovieItem(movieItem, viewModel) {

    override fun bind(binding: ItemMovieBindingImpl, position: Int) {
        binding.movie = movieItem
        binding.viewModel = viewModel

        //set tag so on heart button click movie will be removed from db
        binding.root.add_watchlist_btn.tag = REMOVE_ACTION_TAG
        //change icon
        binding.root.add_watchlist_btn.setImageResource(R.drawable.ic_delete_violet_24dp)

    }
}