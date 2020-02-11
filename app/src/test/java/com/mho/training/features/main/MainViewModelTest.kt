package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.usecases.*
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase

    @Mock
    lateinit var getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase

    @Mock
    lateinit var getPopularMovieListUseCase: GetPopularMovieListUseCase

    @Mock
    lateinit var getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase

    @Mock
    lateinit var getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase

    @Spy
    lateinit var observerEvents: Observer<Event<MainViewModel.Navigation>>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
        viewModel = MainViewModel(
            getFavoriteMovieListUseCase,
            getFavoriteMovieListWithChangesUseCase,
            getPopularMovieListUseCase,
            getTopRatedMovieListUseCase,
            getInTheatersMovieListUseCase,
        Dispatchers.Unconfined
        )
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        viewModel.events.observeForever(observerEvents)

        verify(observerEvents).onChanged(Event(MainViewModel.Navigation.RequestLocationPermission))
    }
}