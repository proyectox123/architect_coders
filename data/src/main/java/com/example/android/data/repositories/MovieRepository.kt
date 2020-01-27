package com.example.android.data.repositories

import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.domain.Movie
import com.example.android.domain.MovieCarousel
import com.example.android.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val localMovieDataSource: LocalMovieDataSource,
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val regionRepository: RegionRepository
) {

    //region Public Methods

    suspend fun getMovieCarouselList(): DataResult<List<MovieCarousel>>{
        val movieCarouselList = mutableListOf<MovieCarousel>()

        movieCarouselList.add(getTopRatedMovieCarouselList())
        movieCarouselList.add(getFavoriteMovieCarouselList())
        movieCarouselList.add(getPopularMovieCarouselList())
        movieCarouselList.add(getInTheatersMovieCarouselList())

        return DataResult.Success(movieCarouselList)
    }

    private suspend fun getTopRatedMovieCarouselList(): MovieCarousel{
        val aux = when(val result = getTopRatedMovieList()){
            is DataResult.Success -> result.data
            is DataResult.Error -> mutableListOf()
        }

        return MovieCarousel("Top Rated", aux)
    }

    private suspend fun getFavoriteMovieCarouselList(): MovieCarousel{
        val aux = when(val result = getFavoriteMovieList()){
            is DataResult.Success -> result.data
            is DataResult.Error -> mutableListOf()
        }

        return MovieCarousel("Favorites", aux)
    }

    private suspend fun getPopularMovieCarouselList(): MovieCarousel{
        val aux = when(val result = getPopularMovieList()){
            is DataResult.Success -> result.data
            is DataResult.Error -> mutableListOf()
        }

        return MovieCarousel("Popular", aux)
    }

    private suspend fun getInTheatersMovieCarouselList(): MovieCarousel{
        val aux = when(val result = getInTheatersMovieList()){
            is DataResult.Success -> result.data
            is DataResult.Error -> mutableListOf()
        }

        return MovieCarousel("In Theaters", aux)
    }

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