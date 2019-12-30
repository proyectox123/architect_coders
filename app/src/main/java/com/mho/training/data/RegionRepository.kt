package com.mho.training.data

import com.mho.training.data.PermissionChecker.Permission.COARSE_LOCATION
import com.mho.training.data.source.LocationDataSource

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