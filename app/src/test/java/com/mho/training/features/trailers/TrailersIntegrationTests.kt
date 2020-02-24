package com.mho.training.features.trailers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Trailer
import com.example.android.testshared.defaultFakeTrailers
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetTrailerListUseCase
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
class TrailersIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerTrailers: Observer<List<Trailer>>

    private lateinit var viewModel: TrailersViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> TrailersViewModel(id, get(), get()) }
            factory { GetTrailerListUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie.copy(id = 1).id) }
    }

    @Test
    fun `onKeywordsFromMovie should load success credit list`() {
        runBlocking {
            //GIVEN
            viewModel.trailers.observeForever(observerTrailers)

            //WHEN
            viewModel.onTrailersFromMovie()

            //THEN
            verify(observerTrailers).onChanged(defaultFakeTrailers)
        }
    }
}