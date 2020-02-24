package com.mho.training.features.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Review
import com.example.android.testshared.defaultFakeReviews
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetReviewListUseCase
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
class ReviewsIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerReviews: Observer<List<Review>>

    private lateinit var viewModel: ReviewsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> ReviewsViewModel(id, get(), get()) }
            factory { GetReviewListUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie.copy(id = 1).id) }
    }

    @Test
    fun `onKeywordsFromMovie should load success credit list`() {
        runBlocking {
            //GIVEN
            viewModel.reviews.observeForever(observerReviews)

            //WHEN
            viewModel.onReviewsFromMovie()

            //THEN
            verify(observerReviews).onChanged(defaultFakeReviews)
        }
    }
}