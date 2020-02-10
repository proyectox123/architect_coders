package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert
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
    fun `is favorite movie list with changes invoke success`() {
        // GIVEN
        val movie = mockedMovie.copy(id = 1)
        val movieListFlow: Flow<List<Movie>> = flow { listOf(movie) }

        whenever(movieRepository.getFavoriteMovieListWithChanges()).thenReturn(movieListFlow)

        // WHEN
        val result: Flow<List<Movie>> = getFavoriteMovieListWithChangesUseCase.invoke()

        // THEN
        Assert.assertEquals(movieListFlow, result)
    }

    @Test
    fun `is favorite movie list with changes invoke fail`() {
        // GIVEN
        val movieListFlow: Flow<List<Movie>> = flow { emptyList<Movie>() }

        whenever(movieRepository.getFavoriteMovieListWithChanges()).thenReturn(movieListFlow)

        // WHEN
        val result: Flow<List<Movie>> = getFavoriteMovieListWithChangesUseCase.invoke()

        // THEN
        Assert.assertEquals(movieListFlow, result)
    }

}