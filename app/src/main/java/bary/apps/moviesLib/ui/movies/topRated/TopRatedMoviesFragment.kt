package bary.apps.moviesLib.ui.movies.topRated

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.TopRatedMoviesFragmentBinding
import bary.apps.moviesLib.util.RecyclerItemClick
import bary.apps.moviesLib.ui.base.ScopedFragment
import bary.apps.moviesLib.ui.movies.MovieItem
import bary.apps.moviesLib.ui.movies.newest.LAST_VISIBLE_PAGE_ITEMS
import bary.apps.moviesLib.ui.movies.newest.PAGE_ITEMS_COUNT
import bary.apps.moviesLib.util.MovieToMovieItemConverter
import bary.apps.moviesLib.util.RecyclerViewScrollListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TopRatedMoviesFragment : ScopedFragment(),
    RecyclerItemClick, RecyclerViewScrollListener,
    MovieToMovieItemConverter, KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: TopRatedMoviesViewModelFactory by instance()

    override lateinit var viewModel: TopRatedMoviesViewModel
    private lateinit var binding: TopRatedMoviesFragmentBinding

    override val fragment: Fragment
        get() = this

    override lateinit var mRecyclerView: RecyclerView
    override lateinit var mRecyclerScrollListener: RecyclerView.OnScrollListener
    private lateinit var mGroupAdapter: GroupAdapter<ViewHolder>
    override lateinit var mBottomProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.top_rated_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TopRatedMoviesViewModel::class.java)
        mGroupAdapter = GroupAdapter<ViewHolder>()
        mRecyclerView = recycler_view_newest_movies
        mBottomProgressBar = bottom_progress_bar

        group_loading.startShimmerAnimation()
        bindUI()
    }

    private fun bindUI() = launch {
        val movies = viewModel.fetchPageOfTopRatedMovies(1).value.await()
        movies.observe(viewLifecycleOwner, Observer {
            if(it == null ) return@Observer

            //deactivate loading view
            group_loading.stopShimmerAnimation()
            group_loading.visibility = View.GONE
            //init recycler view
            initRecyclerView(toMoviesEntries(it.movies))
            //set scroll listener when there is more pages to show
            if(it.page < it.totalPages){
                val nextPage = it.page.inc()

                setRecyclerViewScrollListener(nextPage){
                    viewModel.getNextPageOfTopRatedMovies(nextPage)
                }
            }
        })
    }

    private fun initRecyclerView(movies: List<MovieItem>) {
        mGroupAdapter.apply {
            addAll(movies)
        }

        recycler_view_newest_movies.apply {
            layoutManager = LinearLayoutManager(this@TopRatedMoviesFragment.context)
            adapter = mGroupAdapter
            //scroll to previous position when more items were added to adapter
            if(mGroupAdapter.itemCount > PAGE_ITEMS_COUNT) {
                val lastVisibleItems = mGroupAdapter.itemCount - LAST_VISIBLE_PAGE_ITEMS
                val lastVisibleItemsPosition = mGroupAdapter.getAdapterPosition( mGroupAdapter.getItem(lastVisibleItems) )
                scrollToPosition(lastVisibleItemsPosition)
            }
        }

        mGroupAdapter.setOnItemClickListener { item, _ ->
            (item as? MovieItem)?.let {
                showMovieDetails(it.movieItem.id.toString(), it.movieItem)
            }
        }
    }

}
