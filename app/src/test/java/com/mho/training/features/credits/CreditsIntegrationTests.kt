package com.mho.training.features.credits

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Credit
import com.example.android.testshared.defaultFakeCredits
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
class CreditsIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerCredits: Observer<List<Credit>>

    private lateinit var viewModel: CreditsViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(CreditsFragmentModule(mockedMovie.id)).creditsViewModel
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