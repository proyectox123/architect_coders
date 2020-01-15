package com.example.android.data.repositories

import com.example.android.data.sources.LocalDataSource
import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository
) {

    //region Public Methods

    suspend fun getTopRatedMovieList(): DataResult<List<Movie>> =
        remoteDataSource.getTopRatedMovieList(regionRepository.findLastRegion())

    suspend fun getPopularMovieList(): DataResult<List<Movie>> =
        remoteDataSource.getPopularMovieList(regionRepository.findLastRegion())

    suspend fun getInTheatersMovieList(): DataResult<List<Movie>> =
        remoteDataSource.getInTheatersMovieList(regionRepository.findLastRegion())

    suspend fun getMovieListByPerson(personId: Int): DataResult<List<Movie>> =
        remoteDataSource.getMovieListByPerson(personId)

    suspend fun getFavoriteMovieList(): DataResult<List<Movie>> =
        localDataSource.getFavoriteMovieList()

    fun getFavoriteMovieListWithChanges(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovieListWithChanges()

    suspend fun getFavoriteMovieStatus(movie: Movie) =
        localDataSource.getFavoriteMovieStatus(movie)

    suspend fun updateFavoriteMovieStatus(movie: Movie) =
        localDataSource.updateFavoriteMovieStatus(movie)

    //endregion

}