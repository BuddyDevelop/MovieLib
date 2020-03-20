package bary.apps.moviesLib.ui.movies.topRated

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bary.apps.moviesLib.R

class TopRatedMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = TopRatedMoviesFragment()
    }

    private lateinit var viewModel: TopRatedMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_rated_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopRatedMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
