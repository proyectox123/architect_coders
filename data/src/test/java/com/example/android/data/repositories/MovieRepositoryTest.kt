package com.example.android.data.repositories

import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedMovieDetail
import com.example.android.testshared.mockedPerson
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp(){
        movieRepository = MovieRepository(
            localMovieDataSource, remoteMovieDataSource, regionRepository
        )
    }

    @Test
    fun `getTopRatedMovieList from remote data source should return expected success list of movies with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(remoteMovieDataSource.getTopRatedMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getTopRatedMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getTopRatedMovieList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteMovieDataSource.getTopRatedMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getTopRatedMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getPopularMovieList from remote data source should return expected success list of movies with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(remoteMovieDataSource.getPopularMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getPopularMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getPopularMovieList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteMovieDataSource.getPopularMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getPopularMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getInTheatersMovieList from remote data source should return expected success list of movies with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(remoteMovieDataSource.getInTheatersMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getInTheatersMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getInTheatersMovieList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteMovieDataSource.getInTheatersMovieList(regionRepository.findLastRegion()))
                .willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getInTheatersMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieListByPerson from remote data source should return expected success list of movies with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(remoteMovieDataSource.getMovieListByPerson(person.id)).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getMovieListByPerson(person.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieListByPerson from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteMovieDataSource.getMovieListByPerson(person.id)).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getMovieListByPerson(person.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieDetailById from remote data source should return expected success movie detail with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val movieDetail = mockedMovieDetail.copy(id = 1)

            val expectedDataResult = DataResult.Success(movieDetail)

            given(remoteMovieDataSource.getMovieDetailById(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getMovieDetailById(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieDetailById from remote data source should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteMovieDataSource.getMovieDetailById(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getMovieDetailById(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieList from local data source should return expected success list of movies`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(localMovieDataSource.getFavoriteMovieList()).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getFavoriteMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieList from local data source should return expected error`() {
        runBlocking {

            //GIVEN
            val expectedDataResult = DataResult.Error(IOException(""))

            given(localMovieDataSource.getFavoriteMovieList()).willReturn(expectedDataResult)

            //WHEN
            val result = movieRepository.getFavoriteMovieList()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieListWithChanges from local data source should return expected success list of movies`() {
        //GIVEN
        val movie = mockedMovie.copy(id = 1)

        val expectedDataResult: Flow<List<Movie>> = flow { listOf(movie) }

        given(localMovieDataSource.getFavoriteMovieListWithChanges()).willReturn(expectedDataResult)

        //WHEN
        val result = movieRepository.getFavoriteMovieListWithChanges()

        //THEN
        assertThat(expectedDataResult, `is`(result))
    }

    @Test
    fun `getFavoriteMovieListWithChanges from local data source should return expected empty list`() {
        //GIVEN
        val expectedDataResult: Flow<List<Movie>> = flow { emptyList<Movie>() }

        given(localMovieDataSource.getFavoriteMovieListWithChanges()).willReturn(expectedDataResult)

        //WHEN
        val result = movieRepository.getFavoriteMovieListWithChanges()

        //THEN
        assertThat(expectedDataResult, `is`(result))
    }

    @Test
    fun `getFavoriteMovieStatus from local data source should return true with a favorite movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = true

            given(localMovieDataSource.getFavoriteMovieStatus(movie)).willReturn(true)

            //WHEN
            val result = movieRepository.getFavoriteMovieStatus(movie)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieStatus from local data source should return false with a not favorite movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = false

            given(localMovieDataSource.getFavoriteMovieStatus(movie)).willReturn(false)

            //WHEN
            val result = movieRepository.getFavoriteMovieStatus(movie)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `updateFavoriteMovieStatus from local data source should return true when update favorite movie status with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = true

            given(localMovieDataSource.updateFavoriteMovieStatus(movie)).willReturn(true)

            //WHEN
            val result = movieRepository.updateFavoriteMovieStatus(movie)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `updateFavoriteMovieStatus from local data source should return false when update favorite movie status with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = false

            given(localMovieDataSource.updateFavoriteMovieStatus(movie)).willReturn(false)

            //WHEN
            val result = movieRepository.updateFavoriteMovieStatus(movie)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}