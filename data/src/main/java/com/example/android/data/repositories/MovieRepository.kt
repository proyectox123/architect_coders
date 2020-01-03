package com.example.android.data.repositories

import com.example.android.data.sources.LocalDataSource
import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository
) {

    //region Public Methods

    suspend fun getTopRatedMovieList(): List<Movie> =
        remoteDataSource.getTopRatedMovieList(regionRepository.findLastRegion())

    suspend fun getPopularMovieList(): List<Movie> =
        remoteDataSource.getPopularMovieList(regionRepository.findLastRegion())

    suspend fun getFavoriteMovieList(): List<Movie> =
        localDataSource.getFavoriteMovieList()

    //endregion

}