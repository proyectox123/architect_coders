package com.mho.training.features.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.testshared.defaultFakeFavoriteMovieStatus
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
class MovieDetailIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerIsFavorite: Observer<Boolean>

    private lateinit var viewModel: MovieDetailViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(MovieDetailActivityModule(mockedMovie)).movieDetailViewModel
    }

    @Test
    fun `onValidateFavoriteMovieStatus should return true when favorite movie status is validate`() {
        runBlocking {

            //GIVEN
            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(defaultFakeFavoriteMovieStatus)
        }
    }

    @Test
    fun `updateFavoriteMovieStatus should return true when favorite movie status is updated`() {
        runBlocking {

            //GIVEN
            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(defaultFakeFavoriteMovieStatus)
        }
    }
}