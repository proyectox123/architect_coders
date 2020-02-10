package com.example.android.data.repositories

import com.example.android.data.repositories.PermissionChecker.Permission.COARSE_LOCATION
import com.example.android.data.sources.LocationDataSource
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

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
            val expectedDataResult = RegionRepository.DEFAULT_REGION

            given(permissionChecker.check(COARSE_LOCATION)).willReturn(false)

            //WHEN
            val result = regionRepository.findLastRegion()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `returns region from location data source when permission granted`() {
        runBlocking {

            //GIVEN
            val expectedDataResult = "ES"

            given(permissionChecker.check(COARSE_LOCATION)).willReturn(true)
            given(locationDataSource.findLastRegion()).willReturn(expectedDataResult)

            //WHEN
            val result = regionRepository.findLastRegion()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}