package bary.apps.moviesLib.ui.movies.newest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.databinding.NewestMoviesFragmentBinding
import bary.apps.moviesLib.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.newest_movies_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class NewestMoviesFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: NewestMoviesViewModelFactory by instance()

//    companion object {
//        fun newInstance() = NewestMoviesFragment()
//    }

    private lateinit var viewModel: NewestMoviesViewModel
    private lateinit var binding: NewestMoviesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.newest_movies_fragment, container, false)
        return binding.root
//        return inflater.inflate(R.layout.newest_movies_fragment, container, false)
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

    private fun MoviesResponse.toMoviesEntries(): List<NewestMoviesItem> {
        return this.movies.map {
            NewestMoviesItem(it)
        }
    }

    private fun initRecyclerView(items: List<NewestMoviesItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recycler_view_newest_movies.apply {
            layoutManager = LinearLayoutManager(this@NewestMoviesFragment.context)
            adapter = groupAdapter
        }
        
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? NewestMoviesItem)?.let {
                Toast.makeText(this@NewestMoviesFragment.context, it.movieItem.title, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
