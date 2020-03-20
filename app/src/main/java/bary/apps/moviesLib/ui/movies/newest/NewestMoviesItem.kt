package bary.apps.moviesLib.ui.movies.newest

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.BASE_POSTER_PATH
import bary.apps.moviesLib.data.database.entity.Movie
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_newest_movies.*
import kotlinx.android.synthetic.main.item_newest_movies.view.*

const val POPULARITY_HIGH = 80
const val POPULARITY_LOW = 40

class NewestMoviesItem(
    val movieItem: Movie
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            title_newest_movies.text = movieItem.title
            vote_newest_movies.text = movieItem.voteAverage.toString()
            vote_count_newest_movies.text = movieItem.voteCount.toString()
            release_date_newest_movies.text = movieItem.releaseDate
            popularity_newest_movies.text = movieItem.popularity.toInt().toString()

            loadMovieImage()
            changePopularityBgColor()
            heartButtonClick()
        }
    }

    override fun getLayout() = R.layout.item_newest_movies

    private fun ViewHolder.loadMovieImage(){
        Glide.with(this.containerView)
            .load(BASE_POSTER_PATH + movieItem.posterPath)
            .into(poster_image_newest_movies)

    }

    private fun ViewHolder.changePopularityBgColor(){
        if(movieItem.popularity > POPULARITY_HIGH)
            popularity_newest_movies.setBackgroundColor(Resources.getSystem().getColor(android.R.color.holo_green_dark))
        else if(movieItem.popularity < POPULARITY_LOW)
            popularity_newest_movies.setBackgroundColor(Resources.getSystem().getColor(android.R.color.holo_red_light))
        else
            popularity_newest_movies.setBackgroundColor(Resources.getSystem().getColor(android.R.color.holo_orange_light))
    }

    private fun ViewHolder.heartButtonClick(){
//        heart_button_newest_movies.setOnClickListener {
//            Log.e(title_newest_movies.text.toString(), ": $popularity_newest_movies")
//
//
//        }
    }
}