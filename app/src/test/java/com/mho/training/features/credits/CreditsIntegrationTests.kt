package com.mho.training.features.credits

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Credit
import com.example.android.testshared.defaultFakeCredits
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetCreditListUseCase
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
class CreditsIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerCredits: Observer<List<Credit>>

    private lateinit var viewModel: CreditsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> CreditsViewModel(id, get(), get()) }
            factory { GetCreditListUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie.copy(id = 1).id) }
    }

    @Test
    fun `onCreditsFromMovie should load success credit list`() {
        runBlocking {
            //GIVEN
            viewModel.credits.observeForever(observerCredits)

            //WHEN
            viewModel.onCreditsFromMovie()

            //THEN
            verify(observerCredits).onChanged(defaultFakeCredits)
        }
    }

}