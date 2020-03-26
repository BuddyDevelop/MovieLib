package bary.apps.moviesLib.ui.movies.mostViewed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bary.apps.moviesLib.R


class MostViewedMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MostViewedMoviesFragment()
    }

    private lateinit var viewModel: MostViewedMoviesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.most_viewed_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MostViewedMoviesViewModel::class.java)
        // TODO: Use the ViewModel

    }

}
