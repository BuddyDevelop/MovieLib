package bary.apps.moviesLib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.ActivityMainBinding
import bary.apps.moviesLib.ui.movies.mostViewed.MostViewedMoviesFragment
import bary.apps.moviesLib.ui.movies.newest.NewestMoviesFragment
import bary.apps.moviesLib.ui.movies.topRated.TopRatedMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )

        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewestMoviesFragment(), resources.getString(R.string.newest_movies))
        adapter.addFragment(MostViewedMoviesFragment(), resources.getString(R.string.most_viewed_movies))
        adapter.addFragment(TopRatedMoviesFragment(), resources.getString(R.string.top_rated_movies))
//        adapter.addFragment(MovieDetailFragment(), resources.getString(R.string.details_movie))
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }

}
