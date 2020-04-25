package com.mho.training.features.keywords

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Keyword
import com.example.android.testshared.defaultFakeKeywords
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
class KeywordsIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerKeywords: Observer<List<Keyword>>

    private lateinit var viewModel: KeywordsViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        val movie = mockedMovie.copy(id = 1)
        viewModel = component.plus(KeywordsFragmentModule(movie.id)).keywordsViewModel
    }

    @Test
    fun `onKeywordsFromMovie should load success credit list`() {
        runBlocking {
            //GIVEN
            viewModel.keywords.observeForever(observerKeywords)

            //WHEN
            viewModel.onKeywordsFromMovie()

            //THEN
            verify(observerKeywords).onChanged(defaultFakeKeywords)
        }
    }
}