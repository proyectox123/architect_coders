package com.mho.training.di

import com.example.android.data.repositories.PermissionChecker
import com.example.android.data.sources.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun localMovieDataSourceProvider(): LocalMovieDataSource =
        FakeLocalMovieDataSource()

    @Provides
    @Singleton
    fun remoteMovieDataSourceProvider(): RemoteMovieDataSource =
        FakeRemoteMovieDataSource()

    @Singleton
    @Provides
    fun locationDataSourceProvider(): LocationDataSource =
        FakeLocationDataSource()

    @Singleton
    @Provides
    fun permissionCheckerProvider(): PermissionChecker =
        FakePermissionChecker()

    @Provides
    @Singleton
    fun remoteCreditDataSourceProvider(): RemoteCreditDataSource =
        FakeRemoteCreditDataSource()

    @Provides
    @Singleton
    fun remoteKeywordDataSourceProvider(): RemoteKeywordDataSource =
        FakeRemoteKeywordDataSource()

    @Provides
    @Singleton
    fun remotePersonDataSourceProvider(): RemotePersonDataSource =
        FakeRemotePersonDataSource()

    @Provides
    @Singleton
    fun remoteReviewDataSourceProvider(): RemoteReviewDataSource =
        FakeRemoteReviewDataSource()

    @Provides
    @Singleton
    fun remoteTrailerDataSourceProvider(): RemoteTrailerDataSource =
        FakeRemoteTrailerDataSource()

    @Provides
    @Singleton
    fun remoteLogInDataSourceProvider(): RemoteLogInDataSource =
        FakeLogInServerDataSource()
}