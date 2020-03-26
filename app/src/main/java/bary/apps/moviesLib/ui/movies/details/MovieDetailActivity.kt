package bary.apps.moviesLib.ui.movies.details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.network.response.Video
import bary.apps.moviesLib.databinding.ActivityMovieDetailBinding
import bary.apps.moviesLib.internal.MovieNotFoundException
import bary.apps.moviesLib.ui.base.ScopedActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory


class MovieDetailActivity : ScopedActivity(), KodeinAware {
    override val kodein by closestKodein()
//    private val viewModelFactory: MovieDetailViewModelFactory by instance()
    private val viewModelFactoryInstanceFactory: ((String) -> MovieDetailViewModelFactory) by factory()

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        val movieId = intent.getStringExtra("MovieId") ?: throw MovieNotFoundException()
        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(movieId))
            .get(MovieDetailViewModel::class.java)

        lifecycle.addObserver(video)

        loadMovieDetails()
        loadTrailer()
    }

    private fun loadMovieDetails() = launch{
        val movie = viewModel.movie.await()
        movie.observe(this@MovieDetailActivity, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            binding.movie = it
        })
    }

    private fun loadTrailer() = launch {
        val movieVideos = viewModel.movieVideos.await()
        movieVideos.observe(this@MovieDetailActivity, Observer {
            if(it == null) return@Observer

            //cuz library does not provide data binding setters and adapter is not possible to append
            val trailerTypeMovie = it.videos.first { video -> video.type == "Trailer" }
            initYouTubePlayer(trailerTypeMovie)
        })
    }

    private fun initYouTubePlayer(trailerTypeMovie: Video) {
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.cueVideo(trailerTypeMovie.key, 0.0F)
//                addFullScreenListenerToPlayer()
            }
        })
    }

    private fun addFullScreenListenerToPlayer() {
        video.addFullScreenListener(object : YouTubePlayerFullScreenListener{
            override fun onYouTubePlayerEnterFullScreen() {
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                video.enterFullScreen()

                addCustomActionsToPlayer()
            }

            override fun onYouTubePlayerExitFullScreen() {
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                video.exitFullScreen()
            }

        })
    }

    private fun addCustomActionsToPlayer() {
        val customAction1Icon =
            ContextCompat.getDrawable(this, R.drawable.ic_fast_rewind_white_24dp)
        val customAction2Icon =
            ContextCompat.getDrawable(this, R.drawable.ic_fast_forward_white_24dp)
        assert(customAction1Icon != null)
        assert(customAction2Icon != null)

        video.getPlayerUiController().setCustomAction1(
            customAction1Icon!!,
            null)
        video.getPlayerUiController().setCustomAction2(
            customAction2Icon!!,
            null)
    }

    override fun onBackPressed() {
        if (video.isFullScreen()) video.exitFullScreen() else super.onBackPressed()
    }
}
