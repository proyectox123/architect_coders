package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMovieListWithChangesUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase

    @Before
    fun setUp() {
        getFavoriteMovieListWithChangesUseCase = GetFavoriteMovieListWithChangesUseCase(movieRepository)
    }

    @Test
    fun `getFavoriteMovieListWithChangesUseCase should return expected success list of movies`(){
        // GIVEN
        val movie = mockedMovie.copy(id = 1)

        val expectedDataResult: Flow<List<Movie>> = flow { listOf(movie) }

        given(movieRepository.getFavoriteMovieListWithChanges()).willReturn(expectedDataResult)

        // WHEN
        val result: Flow<List<Movie>> = getFavoriteMovieListWithChangesUseCase.invoke()

        // THEN
        assertThat(expectedDataResult, `is`(result))
    }

    @Test
    fun `getFavoriteMovieListWithChangesUseCase should return expected empty list`(){
        // GIVEN
        val expectedDataResult: Flow<List<Movie>> = flow { emptyList<Movie>() }

        given(movieRepository.getFavoriteMovieListWithChanges()).willReturn(expectedDataResult)

        // WHEN
        val result: Flow<List<Movie>> = getFavoriteMovieListWithChangesUseCase.invoke()

        // THEN
        assertThat(expectedDataResult, `is`(result))
    }

}