package com.mho.training.features.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Credit
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedCredit
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedMovieDetail
import com.example.android.usecases.GetCreditListUseCase
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.GetMovieDetailByIdUseCase
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
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

/*
[functionName]Should[Call|Return|Throw|Insert|etc][Function|Object|Exception][WithGiven][When]
 */

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getCreditListUseCase: GetCreditListUseCase
    @Mock lateinit var getFavoriteMovieStatus: GetFavoriteMovieStatus
    @Mock lateinit var getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase
    @Mock lateinit var updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase

    @Mock lateinit var observerCredits: Observer<List<Credit>>
    @Mock lateinit var observerHasNotCredits: Observer<Boolean>
    @Mock lateinit var observerInfoMovie: Observer<Movie>
    @Mock lateinit var observerIsFavorite: Observer<Boolean>
    @Mock lateinit var observerLoadingCredits: Observer<Boolean>
    @Mock lateinit var observerMovieDetail: Observer<MovieDetail>

    @Mock lateinit var observerEvents: Observer<Event<MovieDetailViewModel.Navigation>>

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp(){
        viewModel = MovieDetailViewModel(
            mockedMovie.copy(id = 1),
            getMovieDetailByIdUseCase,
            getFavoriteMovieStatus,
            updateFavoriteMovieStatusUseCase,
            getCreditListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onMovieInformation should show movie information with given movie`(){

        //GIVEN
        val expectedResult = mockedMovie.copy(id = 1)

        viewModel.infoMovie.observeForever(observerInfoMovie)

        //WHEN
        viewModel.onMovieInformation()

        //THEN
        verify(observerInfoMovie).onChanged(expectedResult)
    }

    @Test
    fun `onMovieInformation should close activity when movie is null`(){

        //GIVEN
        viewModel = MovieDetailViewModel(
            null,
            getMovieDetailByIdUseCase,
            getFavoriteMovieStatus,
            updateFavoriteMovieStatusUseCase,
            getCreditListUseCase,
            Dispatchers.Unconfined
        )

        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieInformation()

        //THEN
        verify(observerEvents).onChanged(Event(MovieDetailViewModel.Navigation.CloseActivity))
    }

    @Test
    fun `getMovieDetailByIdUseCase should return expected success movie detail with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = mockedMovieDetail.copy(id = 1)

            given(getMovieDetailByIdUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerMovieDetail).onChanged(expectedResult)
        }
    }

    @Test
    fun `getMovieDetailByIdUseCase should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            given(getMovieDetailByIdUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerMovieDetail).onChanged(null)
        }
    }

    @Test
    fun `getCreditListUseCase should return expected success credit list with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val credit = mockedCredit.copy(id = 1)

            val expectedResult = listOf(credit)

            given(getCreditListUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.credits.observeForever(observerCredits)
            viewModel.hasNotCredits.observeForever(observerHasNotCredits)
            viewModel.loadingCredits.observeForever(observerLoadingCredits)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerCredits).onChanged(expectedResult)
            verify(observerHasNotCredits).onChanged(false)
            verify(observerLoadingCredits).onChanged(false)
        }
    }

    @Test
    fun `getCreditListUseCase should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = emptyList<Credit>()

            given(getCreditListUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.credits.observeForever(observerCredits)
            viewModel.hasNotCredits.observeForever(observerHasNotCredits)
            viewModel.loadingCredits.observeForever(observerLoadingCredits)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerCredits).onChanged(expectedResult)
            verify(observerHasNotCredits).onChanged(true)
            verify(observerLoadingCredits).onChanged(false)
        }
    }

    @Test
    fun `getFavoriteMovieStatus should return true with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            given(getFavoriteMovieStatus.invoke(movie)).willReturn(true)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(true)
        }
    }

    @Test
    fun `getFavoriteMovieStatus should return false with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            given(getFavoriteMovieStatus.invoke(movie)).willReturn(false)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(false)
        }
    }

    @Test
    fun `updateFavoriteMovieStatusUseCase should return true with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            given(updateFavoriteMovieStatusUseCase.invoke(movie)).willReturn(true)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(true)
        }
    }

    @Test
    fun `updateFavoriteMovieStatusUseCase should return false with given movie`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            given(updateFavoriteMovieStatusUseCase.invoke(movie)).willReturn(false)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(false)
        }
    }
}