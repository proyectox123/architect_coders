package com.example.android.data.repositories

import com.example.android.data.repositories.PermissionChecker.Permission.COARSE_LOCATION
import com.example.android.data.sources.LocationDataSource

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }

    companion object {
        const val DEFAULT_REGION = "US"
    }
}

interface PermissionChecker {
    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}