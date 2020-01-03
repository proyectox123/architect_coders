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

    suspend fun getInTheatersMovieList(): List<Movie> =
        remoteDataSource.getInTheatersMovieList(regionRepository.findLastRegion())

    suspend fun getFavoriteMovieList(): List<Movie> =
        localDataSource.getFavoriteMovieList()

    suspend fun getFavoriteMovieStatus(movie: Movie) =
        localDataSource.getFavoriteMovieStatus(movie)

    suspend fun updateFavoriteMovieStatus(movie: Movie) =
        localDataSource.updateFavoriteMovieStatus(movie)

    //endregion

}