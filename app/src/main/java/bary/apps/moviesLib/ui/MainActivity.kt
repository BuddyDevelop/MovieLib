package bary.apps.moviesLib.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.ActivityMainBinding
import bary.apps.moviesLib.ui.movies.newest.NewestMoviesFragment
import bary.apps.moviesLib.ui.movies.popular.PopularMoviesFragment
import bary.apps.moviesLib.ui.movies.searchMovies.SearchActivity
import bary.apps.moviesLib.ui.movies.topRated.TopRatedMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )
        setSupportActionBar(binding.toolbar)

        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewestMoviesFragment(), resources.getString(R.string.newest_movies))
        adapter.addFragment(PopularMoviesFragment(), resources.getString(R.string.popular_movies))
        adapter.addFragment(TopRatedMoviesFragment(), resources.getString(R.string.top_rated_movies))
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_search -> {
                launchActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun launchActivity() {
        val searchIntent = Intent(this, SearchActivity::class.java)
        startActivity(searchIntent)
    }
}
