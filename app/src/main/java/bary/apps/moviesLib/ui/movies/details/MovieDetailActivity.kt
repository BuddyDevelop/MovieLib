package bary.apps.moviesLib.ui.movies.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.network.response.Video
import bary.apps.moviesLib.databinding.ActivityMovieDetailBinding
import bary.apps.moviesLib.internal.MovieNotFoundException
import bary.apps.moviesLib.ui.base.ScopedActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory


class MovieDetailActivity : ScopedActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory: ((String) -> MovieDetailViewModelFactory) by factory()

    private lateinit var viewModel: MovieDetailViewModel
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

        loadTrailer()
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
}
