package com.example.android.usecases

import com.example.android.data.repositories.ReviewRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedReview
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetReviewListUseCaseTest {

    @Mock
    lateinit var reviewRepository: ReviewRepository

    lateinit var getReviewListUseCase: GetReviewListUseCase

    @Before
    fun setUp(){
        getReviewListUseCase = GetReviewListUseCase(reviewRepository)
    }

    @Test
    fun `is review list invoke success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val review = mockedReview.copy(id = "1")

            val dataResult = DataResult.Success(listOf(review))

            whenever(reviewRepository.getReviewList(movie.id)).thenReturn(dataResult)

            //WHEN

            val result = getReviewListUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is review list invoke fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(reviewRepository.getReviewList(movie.id)).thenReturn(dataResult)

            //WHEN

            val result = getReviewListUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}