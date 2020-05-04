package bary.apps.moviesLib.ui.movies.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.network.response.Review
import bary.apps.moviesLib.data.network.response.Video
import bary.apps.moviesLib.databinding.ActivityMovieDetailBinding
import bary.apps.moviesLib.internal.MovieNotFoundException
import bary.apps.moviesLib.ui.base.ScopedActivity
import bary.apps.moviesLib.ui.movies.MovieItem
import bary.apps.moviesLib.util.MovieToMovieItemConverter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.shashank.sony.fancytoastlib.FancyToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory


class MovieDetailActivity : ScopedActivity(), MovieToMovieItemConverter, KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory: ((String) -> MovieDetailViewModelFactory) by factory()

    override lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        val movieId = intent.getStringExtra("MovieId") ?: throw MovieNotFoundException()
        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(movieId))
            .get(MovieDetailViewModel::class.java)

        val movie: Movie = intent.getParcelableExtra("Movie") ?: throw MovieNotFoundException()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.movie = movie

        lifecycle.addObserver(video)

        initToolbar()
        loadTrailer()
        loadReviews()
        loadSimilarMovies()
    }

    private fun loadTrailer() = launch {
        val movieVideos = viewModel.movieVideos.await()
        movieVideos.observe(this@MovieDetailActivity, Observer {
            if(it == null) return@Observer

            //cuz library does not provide data binding setters so adapter is not possible to append
            //if movie has any video at all
            if(it.videos.isNotEmpty()) {
                val trailerTypeMovie = it.videos.first { video -> video.type == "Trailer" }
                initYouTubePlayer(trailerTypeMovie)
            } else {
                FancyToast.makeText(
                    this@MovieDetailActivity,
                    getString(R.string.no_trailer_available),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    false
                ).show()
            }
        })
    }

    private fun initYouTubePlayer(trailerTypeMovie: Video) {
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.cueVideo(trailerTypeMovie.key, 0.0F)
            }
        })
    }

    private fun loadReviews() = launch {
        val movieReviews = viewModel.movieReviews.await()
        movieReviews.observe(this@MovieDetailActivity, Observer {
            if(it == null)
                return@Observer

            if(it.reviews.isEmpty()) //if no reviews show text
                binding.noReviews.visibility = View.VISIBLE
            else
                binding.noReviews.visibility = View.GONE

            initReviewsRecyclerView(toReviewEntries(it.reviews))
        })
    }

    private fun toReviewEntries(reviews: List<Review>) : List<ReviewItem>{
        return reviews.map {
            ReviewItem(it)
        }
    }

    private fun initReviewsRecyclerView(items: List<ReviewItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        reviews_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity)
            adapter = groupAdapter
        }
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { view -> onBackPressed() }
    }

    private fun loadSimilarMovies() = launch {
        val similarMovies = viewModel.similarMovies.await()
        similarMovies.observe(this@MovieDetailActivity, Observer {
            if(it == null)
                return@Observer

            if(it.movies.isEmpty())
                no_similar_movies.visibility = View.VISIBLE
            else
                no_similar_movies.visibility = View.GONE

            initSimilarMoviesRecyclerView(toMoviesEntries(it.movies))
        })
    }

    private fun initSimilarMoviesRecyclerView(items: List<MovieItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        similar_movies_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            //to be sure click was on movie item
            (item as? MovieItem)?.let {
                showMovieDetails(it.movieItem.id.toString(), it.movieItem)
            }
        }
    }

    private fun showMovieDetails(movieId: String, movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MovieId", movieId)
        intent.putExtra("Movie", movie)
        startActivity(intent)
    }
}
