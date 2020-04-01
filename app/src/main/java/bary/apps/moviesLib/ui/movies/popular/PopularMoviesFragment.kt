package bary.apps.moviesLib.ui.movies.popular

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.PopularMoviesFragmentBinding
import bary.apps.moviesLib.ui.RecyclerItemClick
import bary.apps.moviesLib.ui.base.ScopedFragment
import bary.apps.moviesLib.ui.movies.MovieItem
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModel
import bary.apps.moviesLib.ui.movies.MovieToMovieItemConverter
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class PopularMoviesFragment : ScopedFragment(), MovieToMovieItemConverter, RecyclerItemClick, KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: BaseMoviesViewModelFactory by instance()

    override lateinit var viewModel: BaseMoviesViewModel
    private lateinit var binding: PopularMoviesFragmentBinding
    override val fragment: Fragment
        get() = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.popular_movies_fragment, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseMoviesViewModel::class.java)


        group_loading.startShimmerAnimation()
        bindUI()
    }

    private fun bindUI() = launch {
        val popularMovies = viewModel.popularMovies.await()
        popularMovies.observe(viewLifecycleOwner, Observer {
            if(it == null ) return@Observer

            group_loading.stopShimmerAnimation()
            group_loading.visibility = View.GONE
            initRecyclerView(it.toMoviesEntries())
        })
    }

    private fun initRecyclerView(items: List<MovieItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items.sortedByDescending { it.movieItem.popularity })
        }

        recycler_view_newest_movies.apply {
            layoutManager = LinearLayoutManager(this@PopularMoviesFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            //to be sure click was on movie item
            (item as? MovieItem)?.let {
                showMovieDetails(it.movieItem.id.toString(), it.movieItem)
            }
        }
    }
}
