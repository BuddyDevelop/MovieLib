package bary.apps.moviesLib

import android.app.Application
import bary.apps.moviesLib.data.database.MoviesDatabase
import bary.apps.moviesLib.data.network.*
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSource
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.popularMovies.PopularMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.popularMovies.PopularMoviesNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.searchMovies.SearchMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.searchMovies.SearchMoviesNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.topRatedMovies.TopRatedMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.topRatedMovies.TopRatedMoviesNetworkDataSourceImpl
import bary.apps.moviesLib.data.network.trailers.TrailerNetworkDataSource
import bary.apps.moviesLib.data.network.trailers.TrailerNetworkDataSourceImpl
import bary.apps.moviesLib.data.repository.MoviesRepository
import bary.apps.moviesLib.data.repository.MoviesRepositoryImpl
import bary.apps.moviesLib.ui.MainViewModelFactory
import bary.apps.moviesLib.ui.movies.details.MovieDetailViewModelFactory
import bary.apps.moviesLib.ui.movies.BaseMoviesViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class MoviesApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MoviesApplication))

        bind() from singleton { MoviesDatabase(instance()) }
        bind() from singleton { instance<MoviesDatabase>().movieDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TmdbApiService(instance()) }
        bind<MovieDetailsNetworkDataSource>() with singleton { MovieDetailsNetworkDataSourceImpl(instance()) }
        bind<TrailerNetworkDataSource>() with singleton { TrailerNetworkDataSourceImpl(instance()) }
        bind<MoviesRepository>() with  singleton { MoviesRepositoryImpl(instance(), instance(), instance(), instance(), instance(), instance(), instance()) }
        bind() from factory { movieId: String -> MovieDetailViewModelFactory(instance(), movieId) }
        bind() from provider { BaseMoviesViewModelFactory(instance()) }
        bind<NewestMoviesNetworkDataSource>() with singleton { NewestMoviesNetworkDataSourceImpl(instance()) }
        bind<PopularMoviesNetworkDataSource>() with singleton { PopularMoviesNetworkDataSourceImpl(instance()) }
        bind<TopRatedMoviesNetworkDataSource>() with singleton { TopRatedMoviesNetworkDataSourceImpl(instance()) }
        bind<SearchMoviesNetworkDataSource>() with singleton { SearchMoviesNetworkDataSourceImpl(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}