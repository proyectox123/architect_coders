package com.mho.training.di

import com.example.android.data.repositories.*
import com.example.android.data.sources.*
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun creditRepositoryProvider(
        remoteCreditDataSource: RemoteCreditDataSource
    ) = CreditRepository(remoteCreditDataSource)

    @Provides
    fun keywordRepositoryProvider(
        remoteKeywordDataSource: RemoteKeywordDataSource
    ) = KeywordRepository(remoteKeywordDataSource)

    @Provides
    fun logInRepositoryProvider(
        remoteLogInDataSource: RemoteLogInDataSource
    ) = LogInRepository(remoteLogInDataSource)

    @Provides
    fun movieRepositoryProvider(
        localDataSource: LocalMovieDataSource,
        remoteDataSource: RemoteMovieDataSource,
        regionRepository: RegionRepository
    ) = MovieRepository(localDataSource, remoteDataSource, regionRepository)

    @Provides
    fun personRepositoryProvider(
        remotePersonDataSource: RemotePersonDataSource
    ) = PersonRepository(remotePersonDataSource)

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun reviewRepositoryProvider(
        remoteReviewDataSource: RemoteReviewDataSource
    ) = ReviewRepository(remoteReviewDataSource)

    @Provides
    fun trailerRepositoryProvider(
        remoteTrailerDataSource: RemoteTrailerDataSource
    ) = TrailerRepository(remoteTrailerDataSource)
}