package com.example.android.data.repositories

import com.example.android.data.sources.RemoteReviewDataSource
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
class ReviewRepositoryTest {

    @Mock
    lateinit var remoteReviewDataSource: RemoteReviewDataSource

    private lateinit var reviewRepository: ReviewRepository

    @Before
    fun setUp(){
        reviewRepository = ReviewRepository(remoteReviewDataSource)
    }

    @Test
    fun `getReviewList from remote data source should return expected success list of reviews with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val review = mockedReview.copy(id = "1")

            val expectedDataResult = DataResult.Success(listOf(review))

            given(remoteReviewDataSource.getReviewList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = reviewRepository.getReviewList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getReviewList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteReviewDataSource.getReviewList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = reviewRepository.getReviewList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}