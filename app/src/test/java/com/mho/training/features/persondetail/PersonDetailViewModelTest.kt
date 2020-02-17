package com.mho.training.features.persondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Person
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedPerson
import com.example.android.usecases.GetPersonInformationUseCase
import com.mho.training.utils.Constants
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.atLeast
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
class PersonDetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getPersonInformationUseCase: GetPersonInformationUseCase

    @Mock lateinit var observerHasPersonInformation: Observer<Boolean>
    @Mock lateinit var observerInfoPerson: Observer<Person>
    @Mock lateinit var observerLoadingPerson: Observer<Boolean>
    @Mock lateinit var observerMaxLines: Observer<Int>
    @Mock lateinit var observerShowMoreBiography: Observer<Boolean>

    @Mock lateinit var observerEvents: Observer<Event<PersonDetailViewModel.Navigation>>

    private lateinit var viewModel: PersonDetailViewModel

    @Before
    fun setUp(){
        viewModel = PersonDetailViewModel(
            1,
            getPersonInformationUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `getPersonInformationUseCase should show expected person with given movie id`(){
        runBlocking {
            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val expectedResult = mockedPerson.copy(id = 1)

            given(getPersonInformationUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.infoPerson.observeForever(observerInfoPerson)
            viewModel.loadingPerson.observeForever(observerLoadingPerson)
            viewModel.hasPersonInformation.observeForever(observerHasPersonInformation)

            //WHEN
            viewModel.onCreditInformation()

            //THEN
            verify(observerInfoPerson).onChanged(expectedResult)
            verify(observerLoadingPerson).onChanged(false)
            verify(observerHasPersonInformation).onChanged(true)
        }
    }

    @Test
    fun `getPersonInformationUseCase should close activity with given movie when person is null`(){
        runBlocking {
            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            given(getPersonInformationUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.events.observeForever(observerEvents)
            viewModel.loadingPerson.observeForever(observerLoadingPerson)
            viewModel.hasPersonInformation.observeForever(observerHasPersonInformation)

            //WHEN
            viewModel.onCreditInformation()

            //THEN
            verify(observerLoadingPerson).onChanged(false)
            verify(observerHasPersonInformation).onChanged(false)
            verify(observerEvents).onChanged(Event(PersonDetailViewModel.Navigation.CloseActivity))
        }
    }

    @Test
    fun `showMoreBiography should show more biography lines`(){
        runBlocking {
            //GIVEN

            viewModel.showMoreBiography.observeForever(observerShowMoreBiography)
            viewModel.maxLines.observeForever(observerMaxLines)

            //WHEN
            viewModel.showMoreBiography()

            //THEN
            verify(observerShowMoreBiography).onChanged(false)
            verify(observerMaxLines).onChanged(Constants.MAX_LINES_BIOGRAPHY)
        }
    }

    @Test
    fun `showMoreBiography should show less biography lines`(){
        runBlocking {
            //GIVEN

            viewModel.showMoreBiography.observeForever(observerShowMoreBiography)
            viewModel.maxLines.observeForever(observerMaxLines)

            //WHEN
            viewModel.showLessBiography()

            //THEN
            verify(observerShowMoreBiography, atLeast(1)).onChanged(true)
            verify(observerMaxLines, atLeast(1)).onChanged(Constants.LESS_LINES_BIOGRAPHY)
        }
    }
}