package com.example.android.data.repositories

import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedReview
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

    lateinit var reviewRepository: ReviewRepository

    @Before
    fun setUp(){
        reviewRepository = ReviewRepository(remoteReviewDataSource)
    }

    @Test
    fun `is getReviewList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val review = mockedReview.copy(id = "1")

            val dataResult = DataResult.Success(listOf(review))

            whenever(remoteReviewDataSource.getReviewList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = reviewRepository.getReviewList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getReviewList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteReviewDataSource.getReviewList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = reviewRepository.getReviewList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

}