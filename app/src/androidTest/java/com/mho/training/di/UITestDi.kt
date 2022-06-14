package com.mho.training.di

import android.app.Application
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, RequestModule::class,
    ResourcesModule::class, TestServerModule::class])
interface UiTestComponent: MyMoviesComponent {

    val movieRequest: MovieRequest
    val mockWebServer: MockWebServer

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiTestComponent
    }
}

@Module
class TestServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "http://127.0.0.1:8080"

    @Provides
    @Singleton
    fun mockWebServerProvider(): MockWebServer {
        var mockWebServer:MockWebServer? = null
        val thread = Thread {
            mockWebServer = MockWebServer()
            mockWebServer?.start(8080)
        }
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    fun mockMovieRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = MovieRequest(baseUrl)
}