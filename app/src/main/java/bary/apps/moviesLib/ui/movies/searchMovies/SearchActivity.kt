package bary.apps.moviesLib.ui.movies.searchMovies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.databinding.ActivitySearchBinding
import bary.apps.moviesLib.ui.base.ScopedActivity
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModel
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModelFactory
import bary.apps.moviesLib.ui.movies.MovieItem
import bary.apps.moviesLib.util.MovieToMovieItemConverter
import bary.apps.moviesLib.ui.movies.details.MovieDetailActivity
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SearchActivity : ScopedActivity(),
    MovieToMovieItemConverter, KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: BaseMoviesViewModelFactory by instance()

    private lateinit var binding: ActivitySearchBinding
    override lateinit var viewModel: BaseMoviesViewModel
    private val mOnSearchConfirmedListener = OnSearchConfirmedListener { searchView, query ->
        searchView.collapse()
        bindUI(query)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseMoviesViewModel::class.java)

        initSearchView()
    }

    private fun bindUI(movieName: String) = launch {
        val searchedMovies = viewModel.observeSearchMovies(movieName).value.await()
        searchedMovies.observe(this@SearchActivity, Observer {
            if(it == null)
                return@Observer

            if(it.movies.isEmpty()) //if no result returned show text
                binding.noDataSearch.visibility = View.VISIBLE
            else
                binding.noDataSearch.visibility = View.GONE

            binding.movies = it
            initRecyclerView(toMoviesEntries(it.movies))
        })
    }

    private fun initSearchView() {
        with(persistentSearchView) {
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@SearchActivity))
            setOnSearchConfirmedListener(mOnSearchConfirmedListener)
            setDismissOnTouchOutside(true)
            setDimBackground(true)
            isProgressBarEnabled = true
            isVoiceInputButtonEnabled = true
            isClearInputButtonEnabled = true
            setSuggestionsDisabled(true)
            requestFocus()
        }
    }

    private fun initRecyclerView(items: List<MovieItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        search_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
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


}
