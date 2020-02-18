package com.mho.training.features.keywords

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Keyword
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedKeyword
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetKeywordListUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class KeywordsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getKeywordListUseCase: GetKeywordListUseCase

    @Mock lateinit var observerKeywords: Observer<List<Keyword>>
    @Mock lateinit var observerHasNotKeywords: Observer<Boolean>
    @Mock lateinit var observerLoadingKeywords: Observer<Boolean>

    private lateinit var movie: Movie

    private lateinit var viewModel: KeywordsViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = KeywordsViewModel(
            movie.id,
            getKeywordListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onKeywordsFromMovie should display expected success keyword list with given movie id`() {
        runBlocking {

            //GIVEN
            val keyword = mockedKeyword.copy(id = 1)

            val expectedResult = listOf(keyword)

            given(getKeywordListUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.keywords.observeForever(observerKeywords)
            viewModel.hasNotKeywords.observeForever(observerHasNotKeywords)
            viewModel.loadingKeywords.observeForever(observerLoadingKeywords)

            //WHEN
            viewModel.onKeywordsFromMovie()

            //THEN
            verify(observerKeywords).onChanged(expectedResult)
            verify(observerHasNotKeywords).onChanged(false)
            verify(observerLoadingKeywords).onChanged(false)
        }
    }

    @Test
    fun `onKeywordsFromMovie should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val expectedResult = emptyList<Keyword>()

            given(getKeywordListUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.keywords.observeForever(observerKeywords)
            viewModel.hasNotKeywords.observeForever(observerHasNotKeywords)
            viewModel.loadingKeywords.observeForever(observerLoadingKeywords)

            //WHEN
            viewModel.onKeywordsFromMovie()

            //THEN
            verify(observerKeywords).onChanged(expectedResult)
            verify(observerHasNotKeywords).onChanged(true)
            verify(observerLoadingKeywords).onChanged(false)
        }
    }
}