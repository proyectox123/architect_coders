package com.mho.training.sources

import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.framework.data.remote.models.movie.MovieDetailResult
import com.example.android.framework.data.remote.requests.movie.MovieRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.data.translators.toMovieDetailDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieServerDataSource(
    private val errorUnableToFetchMovies: String,
    private val errorDuringFetchingMovies: String,
    private val voteAverageLabel: String
): RemoteDataSource {

    //region Override Methods & Callbacks

    override suspend fun getTopRatedMovieList(region: String): DataResult<List<Movie>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestTopRatedMovieList(region) },
            errorMessage = errorUnableToFetchMovies
        )
    }

    override suspend fun getPopularMovieList(region: String): DataResult<List<Movie>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestPopularMovieList(region) },
            errorMessage = errorUnableToFetchMovies
        )
    }

    override suspend fun getInTheatersMovieList(region: String): DataResult<List<Movie>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestInTheatersMovieList(region) },
            errorMessage = errorUnableToFetchMovies
        )
    }

    override suspend fun getMovieListByPerson(personId: Int): DataResult<List<Movie>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestMovieListByPerson(personId) },
            errorMessage = errorUnableToFetchMovies
        )
    }

    override suspend fun getMovieDetailById(movieId: Int): DataResult<MovieDetail> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestMovieDetailById(movieId) },
            errorMessage = errorUnableToFetchMovies
        )
    }

    //endregion

    //region Private Methods

    private suspend fun requestTopRatedMovieList(region: String): DataResult<List<Movie>> {
        val response = MovieRequest.service
            .getTopRatedMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainMovie(voteAverageLabel) })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    private suspend fun requestPopularMovieList(region: String): DataResult<List<Movie>> {
        val response = MovieRequest.service
            .getPopularMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainMovie(voteAverageLabel) })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    private suspend fun requestInTheatersMovieList(region: String): DataResult<List<Movie>> {
        val response = MovieRequest.service
            .getInTheatersMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainMovie(voteAverageLabel) })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    private suspend fun requestMovieListByPerson(personId: Int): DataResult<List<Movie>> {
        val response = MovieRequest.service
            .getMovieListByPersonAsync(personId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainMovie(voteAverageLabel) })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    private suspend fun requestMovieDetailById(movieId: Int): DataResult<MovieDetail> {
        val response = MovieRequest.service
            .getMovieDetailByIdAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val result: MovieDetailResult? = response.body()
            if (result != null) {
                return DataResult.Success(result.toMovieDetailDomain())
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    //endregion
}