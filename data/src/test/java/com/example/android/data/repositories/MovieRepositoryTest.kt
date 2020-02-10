package com.example.android.data.repositories

import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedMovieDetail
import com.example.android.mocks.mockedPerson
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    lateinit var localMovieDataSource: LocalMovieDataSource

    @Mock
    lateinit var remoteMovieDataSource: RemoteMovieDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp(){
        movieRepository = MovieRepository(
            localMovieDataSource, remoteMovieDataSource, regionRepository
        )
    }

    @Test
    fun `is getTopRatedMovieList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(remoteMovieDataSource.getTopRatedMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getTopRatedMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getTopRatedMovieList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteMovieDataSource.getTopRatedMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getTopRatedMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getPopularMovieList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(remoteMovieDataSource.getPopularMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getPopularMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getPopularMovieList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteMovieDataSource.getPopularMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getPopularMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getInTheatersMovieList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(remoteMovieDataSource.getInTheatersMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getInTheatersMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getInTheatersMovieList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteMovieDataSource.getInTheatersMovieList(regionRepository.findLastRegion()))
                .thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getInTheatersMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getMovieListByPerson from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(remoteMovieDataSource.getMovieListByPerson(person.id)).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getMovieListByPerson(person.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getMovieListByPerson from remote data source fail`(){
        runBlocking {

            //GIVEN
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteMovieDataSource.getMovieListByPerson(person.id)).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getMovieListByPerson(person.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getMovieDetailById from remote data source success`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val movieDetail = mockedMovieDetail.copy(id = 1)

            val dataResult = DataResult.Success(movieDetail)

            whenever(remoteMovieDataSource.getMovieDetailById(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getMovieDetailById(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getMovieDetailById from remote data source fail`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteMovieDataSource.getMovieDetailById(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getMovieDetailById(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getFavoriteMovieList from local data source success`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(localMovieDataSource.getFavoriteMovieList()).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getFavoriteMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getFavoriteMovieList from local data source fail`() {
        runBlocking {

            //GIVEN
            val dataResult = DataResult.Error(IOException(""))

            whenever(localMovieDataSource.getFavoriteMovieList()).thenReturn(dataResult)

            //WHEN
            val result = movieRepository.getFavoriteMovieList()

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getFavoriteMovieListWithChanges from local data source success`() {
        //GIVEN
        val movie = mockedMovie.copy(id = 1)

        val movieListFlow: Flow<List<Movie>> = flow { listOf(movie) }

        whenever(localMovieDataSource.getFavoriteMovieListWithChanges()).thenReturn(movieListFlow)

        //WHEN
        val result = movieRepository.getFavoriteMovieListWithChanges()

        //THEN
        assertEquals(movieListFlow, result)
    }

    @Test
    fun `is getFavoriteMovieListWithChanges from local data source fail`() {
        //GIVEN
        val movieListFlow: Flow<List<Movie>> = flow { emptyList<Movie>() }

        whenever(localMovieDataSource.getFavoriteMovieListWithChanges()).thenReturn(movieListFlow)

        //WHEN
        val result = movieRepository.getFavoriteMovieListWithChanges()

        //THEN
        assertEquals(movieListFlow, result)
    }

    @Test
    fun `check if selected movie from local data source is favorite`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(localMovieDataSource.getFavoriteMovieStatus(movie)).thenReturn(true)

            //WHEN
            val result = movieRepository.getFavoriteMovieStatus(movie)

            //THEN
            assertEquals(true, result)
        }
    }

    @Test
    fun `check if selected movie from local data source is not favorite`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(localMovieDataSource.getFavoriteMovieStatus(movie)).thenReturn(false)

            //WHEN
            val result = movieRepository.getFavoriteMovieStatus(movie)

            //THEN
            assertEquals(false, result)
        }
    }

    @Test
    fun `become selected movie from local data source favorite`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(localMovieDataSource.updateFavoriteMovieStatus(movie)).thenReturn(true)

            //WHEN
            val result = movieRepository.updateFavoriteMovieStatus(movie)

            //THEN
            assertEquals(true, result)
        }
    }

    @Test
    fun `become selected movie from local data source not favorite`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(localMovieDataSource.updateFavoriteMovieStatus(movie)).thenReturn(false)

            //WHEN
            val result = movieRepository.updateFavoriteMovieStatus(movie)

            //THEN
            assertEquals(false, result)
        }
    }
}