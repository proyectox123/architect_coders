package com.example.android.data.repositories

import com.example.android.data.sources.LocationDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.example.android.data.repositories.PermissionChecker.Permission.COARSE_LOCATION
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default when coarse permission not granted`() {
        runBlocking {

            //GIVEN
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

            //WHEN
            val region = regionRepository.findLastRegion()

            //THEN
            assertEquals(RegionRepository.DEFAULT_REGION, region)
        }
    }

    @Test
    fun `returns region from location data source when permission granted`() {
        runBlocking {

            //GIVEN
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("ES")

            //WHEN
            val region = regionRepository.findLastRegion()

            //THEN
            assertEquals("ES", region)
        }
    }
}