package bary.apps.moviesLib.ui.movies.favourites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.databinding.ActivityFavouritesBinding
import bary.apps.moviesLib.ui.base.ScopedActivity
import bary.apps.moviesLib.ui.movies.*
import bary.apps.moviesLib.ui.movies.details.MovieDetailActivity
import bary.apps.moviesLib.util.MovieToMovieItemConverter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_favourites.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class FavouritesActivity : ScopedActivity(),
    MovieToMovieItemConverter, KodeinAware {
    override val kodein by closestKodein()
    private lateinit var binding: ActivityFavouritesBinding
    override lateinit var viewModel: BaseMoviesViewModel
    private val viewModelFactory: BaseMoviesViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseMoviesViewModel::class.java)

        initToolbar()
        getFavouriteMovies()
    }

    //set toolbar with arrow and change title
    private fun initToolbar(){
        setSupportActionBar(fav_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarTitle = getString(R.string.menu_favourites)
    }

    private fun getFavouriteMovies() = launch {
        viewModel.favouriteMovies.await().observe(this@FavouritesActivity, Observer {
            if(it == null) return@Observer

            binding.noDataFavourites.visibility = View.GONE
            initRecyclerView(toMoviesEntries(it))
        })
    }

    private fun initRecyclerView(items: List<MovieItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        fav_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@FavouritesActivity)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            //to be sure click was on movie item
            (item as? MovieItem)?.let {
                showMovieDetails(it.movieItem.id.toString(), it.movieItem)
            }
        }
    }

    private fun showMovieDetails(movieId: String, movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MovieId", movieId)
        intent.putExtra("Movie", movie)
        startActivity(intent)
    }

    override fun toMoviesEntries(movies: List<Movie>): List<MovieItem> {
        return movies.map {
            FavouriteMovieItem(it, viewModel)
        }
    }


}
