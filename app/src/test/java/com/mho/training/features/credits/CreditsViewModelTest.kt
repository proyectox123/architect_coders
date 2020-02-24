package com.mho.training.features.credits

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Credit
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedCredit
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetCreditListUseCase
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
class CreditsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getCreditListUseCase: GetCreditListUseCase

    @Mock lateinit var observerCredits: Observer<List<Credit>>
    @Mock lateinit var observerHasNotCredits: Observer<Boolean>
    @Mock lateinit var observerLoadingCredits: Observer<Boolean>

    private lateinit var movie: Movie

    private lateinit var viewModel: CreditsViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = CreditsViewModel(
            movie.id,
            getCreditListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onCreditsFromMovie should display expected success credit list with given movie id`() {
        runBlocking {

            //GIVEN
            val credit = mockedCredit.copy(id = 1)

            val expectedResult = listOf(credit)

            given(getCreditListUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.credits.observeForever(observerCredits)
            viewModel.hasNotCredits.observeForever(observerHasNotCredits)
            viewModel.loadingCredits.observeForever(observerLoadingCredits)

            //WHEN
            viewModel.onCreditsFromMovie()

            //THEN
            verify(observerCredits).onChanged(expectedResult)
            verify(observerHasNotCredits).onChanged(false)
            verify(observerLoadingCredits).onChanged(false)
        }
    }

    @Test
    fun `onCreditsFromMovie should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val expectedResult = emptyList<Credit>()

            given(getCreditListUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.credits.observeForever(observerCredits)
            viewModel.hasNotCredits.observeForever(observerHasNotCredits)
            viewModel.loadingCredits.observeForever(observerLoadingCredits)

            //WHEN
            viewModel.onCreditsFromMovie()

            //THEN
            verify(observerCredits).onChanged(expectedResult)
            verify(observerHasNotCredits).onChanged(true)
            verify(observerLoadingCredits).onChanged(false)
        }
    }

}