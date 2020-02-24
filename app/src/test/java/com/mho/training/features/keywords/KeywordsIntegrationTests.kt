package com.mho.training.features.keywords

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Keyword
import com.example.android.testshared.defaultFakeKeywords
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetKeywordListUseCase
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
class KeywordsIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerKeywords: Observer<List<Keyword>>

    private lateinit var viewModel: KeywordsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> KeywordsViewModel(id, get(), get()) }
            factory { GetKeywordListUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie.copy(id = 1).id) }
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