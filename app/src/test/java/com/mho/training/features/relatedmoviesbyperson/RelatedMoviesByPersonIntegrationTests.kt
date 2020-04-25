package com.mho.training.features.relatedmoviesbyperson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.defaultFakeMovies
import com.example.android.testshared.mockedPerson
import com.mho.training.di.DaggerTestComponent
import com.mho.training.di.TestComponent
import com.mho.training.rules.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RelatedMoviesByPersonIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var viewModel: RelatedMoviesByPersonViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(RelatedMoviesByPersonFragmentModule(mockedPerson.id)).relatedMoviesByPersonViewModel
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