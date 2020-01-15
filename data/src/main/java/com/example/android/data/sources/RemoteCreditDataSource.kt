package com.example.android.data.sources

import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult

interface RemoteCreditDataSource {
    suspend fun getCreditList(movieId: Int): DataResult<List<Credit>>
}