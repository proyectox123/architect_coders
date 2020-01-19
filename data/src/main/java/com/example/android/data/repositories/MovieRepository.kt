package com.example.android.data.repositories

import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val localMovieDataSource: LocalMovieDataSource,
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val regionRepository: RegionRepository
) {

    //region Public Methods

    suspend fun getTopRatedMovieList(): DataResult<List<Movie>> =
        remoteMovieDataSource.getTopRatedMovieList(regionRepository.findLastRegion())

    suspend fun getPopularMovieList(): DataResult<List<Movie>> =
        remoteMovieDataSource.getPopularMovieList(regionRepository.findLastRegion())

    suspend fun getInTheatersMovieList(): DataResult<List<Movie>> =
        remoteMovieDataSource.getInTheatersMovieList(regionRepository.findLastRegion())

    suspend fun getMovieListByPerson(personId: Int): DataResult<List<Movie>> =
        remoteMovieDataSource.getMovieListByPerson(personId)

    suspend fun getMovieDetailById(movieId: Int) =
        remoteMovieDataSource.getMovieDetailById(movieId)

    suspend fun getFavoriteMovieList(): DataResult<List<Movie>> =
        localMovieDataSource.getFavoriteMovieList()

    fun getFavoriteMovieListWithChanges(): Flow<List<Movie>> =
        localMovieDataSource.getFavoriteMovieListWithChanges()

    suspend fun getFavoriteMovieStatus(movie: Movie) =
        localMovieDataSource.getFavoriteMovieStatus(movie)

    suspend fun updateFavoriteMovieStatus(movie: Movie) =
        localMovieDataSource.updateFavoriteMovieStatus(movie)

    //endregion

}