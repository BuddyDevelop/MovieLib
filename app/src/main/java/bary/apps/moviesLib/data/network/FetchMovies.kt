package bary.apps.moviesLib.data.network

interface FetchMovies {
    suspend fun fetchMovies(
        voteCount: String,
        sortBy: String,
        languageCode: String
    )
}