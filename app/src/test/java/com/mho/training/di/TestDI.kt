package com.mho.training.di

import com.example.android.data.repositories.PermissionChecker
import com.example.android.data.sources.*
import com.example.android.domain.*
import com.example.android.domain.result.DataResult
import com.example.android.domain.user.LogInParams
import com.example.android.testshared.*
import dagger.Component
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestAppModule::class,
    DataModule::class
])
interface TestComponent: MyMoviesComponent {

    @Component.Factory
    interface FactoryTest {
        fun create(): TestComponent
    }
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

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    private var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}

class FakeLogInServerDataSource : RemoteLogInDataSource {
    override fun logIn(logInParams: LogInParams, success: (String) -> Unit, error: () -> Unit) {
        with(logInParams){
            if(username == "coders" && password == "architect"){
                success("token")
            }else{
                error()
            }
        }
    }

}