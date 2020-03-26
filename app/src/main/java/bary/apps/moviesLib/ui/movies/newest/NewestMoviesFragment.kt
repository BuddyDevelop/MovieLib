package bary.apps.moviesLib.ui.movies.newest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.databinding.NewestMoviesFragmentBinding
import bary.apps.moviesLib.ui.base.ScopedFragment
import bary.apps.moviesLib.ui.movies.MovieItem
import bary.apps.moviesLib.ui.movies.details.MovieDetailActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class NewestMoviesFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: NewestMoviesViewModelFactory by instance()


    private lateinit var viewModel: NewestMoviesViewModel
    private lateinit var binding: NewestMoviesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.newest_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewestMoviesViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch{
        val movies = viewModel.newestMovies.await()
        movies.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            initRecyclerView(it.toMoviesEntries())
        })
    }

    private fun MoviesResponse.toMoviesEntries(): List<MovieItem> {
        return this.movies.map {
            MovieItem(it)
        }
    }

    private fun initRecyclerView(items: List<MovieItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recycler_view_newest_movies.apply {
            layoutManager = LinearLayoutManager(this@NewestMoviesFragment.context)
            adapter = groupAdapter
        }
        
        groupAdapter.setOnItemClickListener { item, _ ->
            //to be sure click was on movie item
            (item as? MovieItem)?.let {
                showMovieDetails(it.movieItem.id.toString())
            }
        }
    }

    private fun showMovieDetails(movieId: String){
        activity?.let { activity ->
            val intent = Intent(requireActivity(), MovieDetailActivity::class.java)
            intent.putExtra("MovieId", movieId)
            activity.startActivity(intent)
        }
    }
}
