package com.mho.training.features.movieinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.testshared.defaultFakeMovieDetail
import com.example.android.testshared.mockedMovie
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
class MovieInfoIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerInfoMovie: Observer<Movie>
    @Mock lateinit var observerMovieDetail: Observer<MovieDetail>

    private lateinit var viewModel: MovieInfoViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(MovieInfoFragmentModule(mockedMovie)).movieInfoViewModel
    }

    @Test
    fun `onMovieInformation should display movie detail information`() {
        runBlocking {

            //GIVEN
            viewModel.infoMovie.observeForever(observerInfoMovie)
            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerInfoMovie).onChanged(mockedMovie)
            verify(observerMovieDetail).onChanged(defaultFakeMovieDetail)
        }
    }
}