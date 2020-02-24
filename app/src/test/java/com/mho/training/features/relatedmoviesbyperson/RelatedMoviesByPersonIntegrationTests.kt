package com.mho.training.features.relatedmoviesbyperson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.defaultFakeMovies
import com.example.android.testshared.mockedPerson
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.mho.training.initMockedDi
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RelatedMoviesByPersonIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var viewModel: RelatedMoviesByPersonViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> RelatedMoviesByPersonViewModel(id, get(), get()) }
            factory { GetMovieListByPersonUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedPerson.id) }
    }

    @Test
    fun `onRelatedMoviesByPerson should display related movies by person`() {
        runBlocking {

            //GIVEN
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onRelatedMoviesByPerson()

            //THEN
            verify(observerMovies).onChanged(defaultFakeMovies)
        }
    }
}