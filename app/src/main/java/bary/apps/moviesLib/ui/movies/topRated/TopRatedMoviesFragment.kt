package bary.apps.moviesLib.ui.movies.topRated

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.TopRatedMoviesFragmentBinding
import kotlinx.android.synthetic.main.activity_movie_detail.view.*
import kotlinx.android.synthetic.main.list_movies.*

class TopRatedMoviesFragment : Fragment() {

    private lateinit var viewModel: TopRatedMoviesViewModel
    private lateinit var binding: TopRatedMoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.top_rated_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopRatedMoviesViewModel::class.java)

        group_loading.startShimmerAnimation()
    }

}
