package bary.apps.moviesLib.data.network

import bary.apps.moviesLib.data.API_KEY
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.response.MoviesResponse
import bary.apps.moviesLib.data.network.response.Reviews
import bary.apps.moviesLib.data.network.response.Videos
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApiService {

    @GET("/3/movie/{movieId}")
    fun getMovieDetailsById(
        @Path("movieId") movieId: String
    ): Deferred<MovieDetails>

    @GET("/3/movie/{movieId}/videos")
    fun getMovieVideos(
        @Path("movieId") movieId: String
    ): Deferred<Videos>

    @GET("/3/movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int
    ) : Deferred<MoviesResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int
    ) : Deferred<MoviesResponse>

    @GET("/3/search/movie")
    fun searchMovies(
        @Query("query") movieName: String
    ) : Deferred<MoviesResponse>

    @GET("/3/discover/movie?")
    fun getMoviesByVoteCountAndSortByRelaseDate(
        @Query("vote_count.gte") voteCount: String,
        @Query("sort_by") sortBy: String = "primary_release_date.desc",
        @Query("language") languageCode: String = "en",
        @Query("page") page: Int = 1
    ): Deferred<MoviesResponse>

    @GET("/3/movie/{movieId}/reviews")
    fun getReviews(
        @Path("movieId") movieId: String
    ) : Deferred<Reviews>

    @GET("/3/movie/{movieId}/similar")
    fun getSimilarMovies(
        @Path("movieId") movieId: String
    ) : Deferred<MoviesResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TmdbApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbApiService::class.java)
        }
    }
}