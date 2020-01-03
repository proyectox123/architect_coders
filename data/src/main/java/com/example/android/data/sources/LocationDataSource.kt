package com.example.android.data.sources

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}