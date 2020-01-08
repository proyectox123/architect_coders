package com.example.android.data.sources

import com.example.android.domain.Credit
import com.example.android.framework.data.remote.requests.Result

interface RemoteCreditDataSource {
    suspend fun getCreditList(movieId: Int): Result<List<Credit>>
}