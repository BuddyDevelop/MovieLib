package bary.apps.moviesLib.ui.movies.mostViewed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.MostViewedMoviesFragmentBinding
import kotlinx.android.synthetic.main.list_movies.*


class MostViewedMoviesFragment : Fragment() {

    private lateinit var viewModel: MostViewedMoviesViewModel
    private lateinit var binding: MostViewedMoviesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.most_viewed_movies_fragment, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MostViewedMoviesViewModel::class.java)


        group_loading.startShimmerAnimation()
    }

}
