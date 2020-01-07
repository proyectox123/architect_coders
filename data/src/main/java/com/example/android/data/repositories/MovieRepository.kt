package com.example.android.data.repositories

import androidx.lifecycle.LiveData
import com.example.android.data.sources.LocalDataSource
import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie
import com.example.android.framework.data.remote.requests.Result

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository
) {

    //region Public Methods

    suspend fun getTopRatedMovieList(): Result<List<Movie>> =
        remoteDataSource.getTopRatedMovieList(regionRepository.findLastRegion())

    suspend fun getPopularMovieList(): Result<List<Movie>> =
        remoteDataSource.getPopularMovieList(regionRepository.findLastRegion())

    suspend fun getInTheatersMovieList(): Result<List<Movie>> =
        remoteDataSource.getInTheatersMovieList(regionRepository.findLastRegion())

    suspend fun getFavoriteMovieList(): Result<List<Movie>> =
        localDataSource.getFavoriteMovieList()

    fun getFavoriteMovieListWithChanges(): LiveData<List<Movie>> =
        localDataSource.getFavoriteMovieListWithChanges()

    suspend fun getFavoriteMovieStatus(movie: Movie) =
        localDataSource.getFavoriteMovieStatus(movie)

    suspend fun updateFavoriteMovieStatus(movie: Movie) =
        localDataSource.updateFavoriteMovieStatus(movie)

    //endregion

}