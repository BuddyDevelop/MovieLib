package bary.apps.moviesLib.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import bary.apps.moviesLib.R
import bary.apps.moviesLib.databinding.ActivityMainBinding
import bary.apps.moviesLib.ui.movies.newest.NewestMoviesFragment
import bary.apps.moviesLib.ui.movies.popular.PopularMoviesFragment
import bary.apps.moviesLib.ui.movies.searchMovies.SearchActivity
import bary.apps.moviesLib.ui.movies.topRated.TopRatedMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

const val NIGHT_UI_MODE: Int = 33
const val NIGHT_MODE_MASK: Int = 48

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )
        setSupportActionBar(binding.toolbar)

        initTabLayout()
        initFloatingNavigationView()
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewestMoviesFragment(), resources.getString(R.string.newest_movies))
        adapter.addFragment(PopularMoviesFragment(), resources.getString(R.string.popular_movies))
        adapter.addFragment(TopRatedMoviesFragment(), resources.getString(R.string.top_rated_movies))
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }

    //toolbar search icon click
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

    //floating navigation view
    private fun initFloatingNavigationView(){
        floating_navigation_view.setOnClickListener{
            floating_navigation_view.open()
        }

        floating_navigation_view.setNavigationItemSelectedListener{ item ->
           when (item.itemId){
               R.id.nav_dark_mode ->{ //night mode changing from documentation does not work
                   val uiMode = this@MainActivity.theme.resources.configuration.uiMode
                   val nightMask = Configuration.UI_MODE_NIGHT_MASK

                   if( uiMode == NIGHT_UI_MODE && nightMask == NIGHT_MODE_MASK )
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                   else
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

               }
               else -> false
           }
            floating_navigation_view.close()
            false
        }
    }

    override fun onBackPressed() {
        if(floating_navigation_view.isOpened)
            floating_navigation_view.close()
        else
            super.onBackPressed()
    }
}
