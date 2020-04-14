package bary.apps.moviesLib.data.repository

import androidx.lifecycle.LiveData
import bary.apps.moviesLib.data.database.MovieDao
import bary.apps.moviesLib.data.database.entity.Movie
import bary.apps.moviesLib.data.network.movieDetails.MovieDetailsNetworkDataSource
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.newestMovies.NewestMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.popularMovies.PopularMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Videos
import bary.apps.moviesLib.data.network.searchMovies.SearchMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.topRatedMovies.TopRatedMoviesNetworkDataSource
import bary.apps.moviesLib.data.network.trailers.TrailerNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource,
    private val newestMoviesNetworkDataSource: NewestMoviesNetworkDataSource,
    private val movieTrailerNetworkDataSource: TrailerNetworkDataSource,
    private val popularMoviesNetworkDataSource: PopularMoviesNetworkDataSource,
    private val topRatedMoviesNetworkDataSource: TopRatedMoviesNetworkDataSource,
    private val searchMoviesNetworkDataSource: SearchMoviesNetworkDataSource
) : MoviesRepository {

    //replace movie in db
    override fun updateMovie(movie: Movie){
        GlobalScope.launch(Dispatchers.IO){
            movieDao.updateMovie(movie)
        }
    }

    //delete movie from db
    override fun deleteMovie(id: Int) {
        GlobalScope.launch(Dispatchers.IO){
            movieDao.deleteMovie(id)
        }
    }

    //if movie with given id not exist in db insert otherwise set movie to FAV
    override fun favMovieInsertOrUpdate(id: Int, movie: Movie){
        GlobalScope.launch(Dispatchers.IO){
            val movieRecord = movieDao.getMovie(id)

            if(movieRecord == null)
                movieDao.updateMovie(movie)
            else
                movieDao.setFavouriteMovie(id)
        }
    }

    //unset fav movie or delete from db if is not in watchlist
    override fun favMovieRemoveOrUpdate(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            val movieRecord = movieDao.getMovie(id)

            if (movieRecord != null) {
                if (movieRecord.isWatchlist == true)
                    movieDao.removeFavouriteMovie(id)
                else
                    movieDao.deleteMovie(id)
            }
        }
    }

    //if movie with given id not exist in db insert otherwise set movie to WATCHLIST
    override fun watchlistMovieInsertOrUpdate(id: Int, movie: Movie){
        GlobalScope.launch(Dispatchers.IO){
            val movieRecord = movieDao.getMovie(id)

            if(movieRecord == null)
                movieDao.updateMovie(movie)
            else
                movieDao.setWatchlistMovie(id)
        }
    }

    //unset watchlist movie or delete from db if is not in favourite
    override fun watchlistMovieRemoveOrUpdate(id: Int){
        val movieRecord = movieDao.getMovie(id)

        if(movieRecord!= null ) {
            if (movieRecord.isFavourite == true)
                movieDao.removeWatchlistMovie(id)
            else
                movieDao.deleteMovie(id)
        }
    }

    //get movie details
    override suspend fun getMovie(movieId: String): LiveData<MovieDetails> {
        return withContext(Dispatchers.IO){
            fetchMovieDetails(movieId)
            return@withContext(movieDetailsNetworkDataSource.downloadedMovieDetails)
        }
    }

    private suspend fun fetchMovieDetails(movieId: String){
        movieDetailsNetworkDataSource.fetchMovieById(movieId)
    }
    //end movies details

    //get list of movies
    override suspend fun getMovies(voteCount: String, sortBy: String, languageCode: String): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            fetchNewestMovies(voteCount, sortBy, languageCode)
            return@withContext(newestMoviesNetworkDataSource.downloadedNewestMovies)
        }
    }

    private suspend fun fetchNewestMovies(voteCount: String, sortBy: String, languageCode: String) {
        newestMoviesNetworkDataSource.fetchMovies(voteCount, sortBy, languageCode)
    }
    //end movie listing

    //get movie trailer
    override suspend fun getMovieTrailers(movieId: String): LiveData<Videos> {
        return withContext(Dispatchers.IO){
            fetchMovieTrailers(movieId)
            return@withContext(movieTrailerNetworkDataSource.downloadedTrailers)
        }
    }

    private suspend fun fetchMovieTrailers(movieId: String) {
        movieTrailerNetworkDataSource.fetchMovieTrailers(movieId)
    }
    //end movie trailer

    //get popular movies
    override suspend fun getMostPopularMovies(): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO){
            fetchPopularMovies()
            return@withContext(popularMoviesNetworkDataSource.downloadedPopularMovies)
        }
    }

    private suspend fun fetchPopularMovies(){
        popularMoviesNetworkDataSource.fetchPopularMovies()
    }
    //end popular movies

    //get top rated movies
    override suspend fun getTopRatedMovies(): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO){
            fetchTopRatedMovies()
            return@withContext(topRatedMoviesNetworkDataSource.downloadedTopRatedMovies)
        }
    }

    private suspend fun fetchTopRatedMovies(){
        topRatedMoviesNetworkDataSource.fetchTopRatedMovies()
    }
    //end top rated movies

    //search movies
    override suspend fun getSearchedMovies(movieName: String): LiveData<MoviesResponse> {
        return withContext(Dispatchers.IO){
            searchMovies(movieName)
            return@withContext(searchMoviesNetworkDataSource.downloadedMovies)
        }
    }

    private suspend fun searchMovies(movieName: String){
        searchMoviesNetworkDataSource.fetchMoviesByName(movieName)
    }
    //end search movies

    override suspend fun getFavouriteMovies(): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO){
            return@withContext(movieDao.getFavouriteMovies())
        }
    }

    override suspend fun getWatchlistMovies(): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO){
            return@withContext(movieDao.getWatchlistMovies())
        }
    }

    override suspend fun getFavouriteMoviesCount(): LiveData<Int> {
        return withContext(Dispatchers.IO){
            return@withContext(movieDao.getFavouriteMoviesCount())
        }
    }

    override suspend fun getWatchlistMoviesCount(): LiveData<Int> {
        return withContext(Dispatchers.IO){
            return@withContext(movieDao.getWatchlistMoviesCount())
        }
    }
}