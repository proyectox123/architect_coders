package com.example.android.usecases

import com.example.android.data.repositories.ReviewRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedReview
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
    fun `getReviewListUseCase should return expected success list of reviews with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val review = mockedReview.copy(id = "1")

            val expectedDataResult = DataResult.Success(listOf(review))

            given(reviewRepository.getReviewList(movie.id)).willReturn(expectedDataResult)

            //WHEN

            val result = getReviewListUseCase.invoke(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getReviewListUseCase should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(reviewRepository.getReviewList(movie.id)).willReturn(expectedDataResult)

            //WHEN

            val result = getReviewListUseCase.invoke(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}