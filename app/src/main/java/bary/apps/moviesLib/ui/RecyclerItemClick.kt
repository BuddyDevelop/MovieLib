package bary.apps.moviesLib.ui

import android.content.Intent
import androidx.fragment.app.Fragment
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.ui.movies.details.MovieDetailActivity

interface RecyclerItemClick {
    val fragment: Fragment

    fun showMovieDetails(
        movieId: String,
        movie: Movie
    ){
        fragment.activity?.let { activity ->
            val intent = Intent(fragment.requireActivity(), MovieDetailActivity::class.java)
            intent.putExtra("MovieId", movieId)
            intent.putExtra("Movie", movie)
            activity.startActivity(intent)
        }
    }
}