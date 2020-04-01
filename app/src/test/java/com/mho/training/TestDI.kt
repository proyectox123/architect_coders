package com.mho.training

import com.example.android.data.repositories.*
import com.example.android.data.sources.*
import com.example.android.domain.*
import com.example.android.domain.result.DataResult
import com.example.android.testshared.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, mockedDataSourceModule, mockedDataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

private val mockedDataSourceModule = module {
    single<LocalMovieDataSource> { FakeLocalMovieDataSource() }
    single<RemoteMovieDataSource> { FakeRemoteMovieDataSource() }
    single<RemoteCreditDataSource> { FakeRemoteCreditDataSource() }
    single<RemoteKeywordDataSource> { FakeRemoteKeywordDataSource() }
    single<RemotePersonDataSource> { FakeRemotePersonDataSource() }
    single<RemoteReviewDataSource> { FakeRemoteReviewDataSource() }
    single<RemoteTrailerDataSource> { FakeRemoteTrailerDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
}

private val mockedDataModule = module {
    factory { CreditRepository(get()) }
    factory { KeywordRepository(get()) }
    factory { MovieRepository(get(), get(), get()) }
    factory { PersonRepository(get()) }
    factory { RegionRepository(get(), get()) }
    factory { ReviewRepository(get()) }
    factory { TrailerRepository(get()) }
}

class FakeLocalMovieDataSource : LocalMovieDataSource {
    override suspend fun getFavoriteMovieList(): DataResult<List<Movie>> =
        DataResult.Success(defaultFakeMovies)

    override fun getFavoriteMovieListWithChanges(): Flow<List<Movie>> =
        flowOf(defaultFakeMovies)

    override suspend fun getFavoriteMovieStatus(movie: Movie) = defaultFakeFavoriteMovieStatus

    override suspend fun updateFavoriteMovieStatus(movie: Movie) = defaultFakeFavoriteMovieStatus

}

class FakeRemoteMovieDataSource : RemoteMovieDataSource {
    override suspend fun getTopRatedMovieList(region: String): DataResult<List<Movie>> =
        DataResult.Success(defaultFakeMovies)

    override suspend fun getPopularMovieList(region: String): DataResult<List<Movie>> =
        DataResult.Success(defaultFakeMovies)

    override suspend fun getInTheatersMovieList(region: String): DataResult<List<Movie>> =
        DataResult.Success(defaultFakeMovies)

    override suspend fun getMovieListByPerson(personId: Int): DataResult<List<Movie>> =
        DataResult.Success(defaultFakeMovies)

    override suspend fun getMovieDetailById(movieId: Int): DataResult<MovieDetail> =
        DataResult.Success(defaultFakeMovieDetail)

}

class FakeRemoteCreditDataSource : RemoteCreditDataSource {
    override suspend fun getCreditList(movieId: Int): DataResult<List<Credit>> =
        DataResult.Success(defaultFakeCredits)
}

class FakeRemoteKeywordDataSource : RemoteKeywordDataSource {
    override suspend fun getKeywordList(movieId: Int): DataResult<List<Keyword>> =
        DataResult.Success(defaultFakeKeywords)
}

class FakeRemotePersonDataSource : RemotePersonDataSource{
    override suspend fun getPerson(personId: Int): DataResult<Person> =
        DataResult.Success(defaultFakedPerson)
}

class FakeRemoteReviewDataSource : RemoteReviewDataSource{
    override suspend fun getReviewList(movieId: Int): DataResult<List<Review>> =
        DataResult.Success(defaultFakeReviews)
}

class FakeRemoteTrailerDataSource : RemoteTrailerDataSource {

    override suspend fun getTrailerList(movieId: Int): DataResult<List<Trailer>> =
        DataResult.Success(defaultFakeTrailers)
}

class FakeLocationDataSource : LocationDataSource {
    private var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    private var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}