@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mho.training.features.trailer.mvi.viewmodel

import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetTrailerListUseCase
import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@FlowPreview
class TrailerActionProcessorTest {

    @RelaxedMockK
    lateinit var getTrailerListUseCase: GetTrailerListUseCase

    private lateinit var trailerActionProcessor: TrailerActionProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        trailerActionProcessor = TrailerActionProcessor(getTrailerListUseCase)
    }

    @Test
    fun `actionToResult - LoadAllTrailerAction returns LoadAllTrailerResult Loading`() = runBlockingTest {
        // given
        val movieId = 1
        val action = TrailerAction.LoadAllTrailerAction(movieId)
        val expected = TrailerResult.LoadAllTrailerResult.Loading

        // when
        val result = trailerActionProcessor.actionToResult(action).first()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `actionToResult - LoadAllTrailerAction returns LoadAllTrailerResult Success`() = runBlockingTest {
        // given
        val movieId = 1
        val trailer = mockk<Trailer>()
        val trailers = listOf(trailer)
        val action = TrailerAction.LoadAllTrailerAction(movieId)
        val expected = TrailerResult.LoadAllTrailerResult.Success(trailers)
        coEvery { getTrailerListUseCase.invoke(movieId) } returns DataResult.Success(trailers)

        // when
        val result = trailerActionProcessor.actionToResult(action).last()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `actionToResult - LoadAllTrailerAction returns LoadAllTrailerResult Failure`() = runBlockingTest {
        // given
        val movieId = 1
        val action = TrailerAction.LoadAllTrailerAction(movieId)
        val exception = Exception()
        val expected = TrailerResult.LoadAllTrailerResult.Failure(exception)
        coEvery { getTrailerListUseCase.invoke(movieId) } returns DataResult.Error(exception)

        // when
        val result = trailerActionProcessor.actionToResult(action).last()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `actionToResult - OpenTrailerAction returns OpenTrailerResult Loading`() = runBlockingTest {
        // given
        val trailer = mockk<Trailer>()
        val action = TrailerAction.OpenTrailerAction(trailer)
        val expected = TrailerResult.OpenTrailerResult.Success(trailer)

        // when
        trailerActionProcessor.actionToResult(action).collect { result ->
            // then
            assertEquals(expected, result)
        }
    }
}
