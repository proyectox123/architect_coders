package com.mho.training.features.relatedmoviesbyperson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class RelatedMoviesByPersonViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getMovieListByPersonUseCase: GetMovieListByPersonUseCase

    @Mock lateinit var observerHasNotMovies: Observer<Boolean>
    @Mock lateinit var observerMovies: Observer<List<Movie>>

    @Mock lateinit var observerEvents: Observer<Event<RelatedMoviesByPersonViewModel.Navigation>>

    private lateinit var viewModel: RelatedMoviesByPersonViewModel

    @Before
    fun setUp(){
        viewModel = RelatedMoviesByPersonViewModel(
            1,
            getMovieListByPersonUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `getMovieListByPersonUseCase should show expected success list of movies with given movie id`(){
        runBlocking {
            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val expectedResult = listOf(movie)

            given(getMovieListByPersonUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.movies.observeForever(observerMovies)
            viewModel.hasNotMovies.observeForever(observerHasNotMovies)

            //WHEN
            viewModel.onRelatedMoviesByPerson()

            //THEN
            verify(observerMovies).onChanged(expectedResult)
            verify(observerHasNotMovies).onChanged(false)
        }
    }

    @Test
    fun `getMovieListByPersonUseCase should show expected error with given movie id`(){
        runBlocking {
            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val expectedResult = emptyList<Movie>()

            given(getMovieListByPersonUseCase.invoke(movie.id)).willReturn(
                DataResult.Error(
                    IOException("")
                ))

            viewModel.movies.observeForever(observerMovies)
            viewModel.hasNotMovies.observeForever(observerHasNotMovies)

            //WHEN
            viewModel.onRelatedMoviesByPerson()

            //THEN
            verify(observerMovies).onChanged(expectedResult)
            verify(observerHasNotMovies).onChanged(true)
        }
    }

    @Test
    fun `onMovieClicked should open movie detail with given movie`(){
        //GIVEN
        val movie = mockedMovie.copy(id = 1)

        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieClicked(movie)

        //THEN
        verify(observerEvents).onChanged(Event(RelatedMoviesByPersonViewModel.Navigation.NavigateToMovie(movie)))
    }

}