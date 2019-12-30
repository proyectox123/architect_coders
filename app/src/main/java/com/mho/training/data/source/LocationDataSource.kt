package com.mho.training.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}