package bary.apps.moviesLib.data.network

import bary.apps.moviesLib.data.API_KEY
import bary.apps.moviesLib.data.database.entity.MovieDetails
import bary.apps.moviesLib.data.network.response.MoviesResponse
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
    fun getMovieById(
        @Path("movieId") movieId: String
    ): Deferred<MovieDetails>

    @GET("/3/discover/movie?")
    fun getMoviesByVoteCountAndSortByRelaseDate(
        @Query("vote_count.gte") voteCount: String,
        @Query("sort_by") sortBy: String = "release_date.desc",
        @Query("language") languageCode: String = "en"
    ): Deferred<MoviesResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TmdbApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
//                    .addQueryParameter("append_to_response", "videos")
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