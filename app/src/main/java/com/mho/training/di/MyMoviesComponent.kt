package com.mho.training.di

import android.app.Application
import com.mho.training.features.credits.CreditsFragmentComponent
import com.mho.training.features.credits.CreditsFragmentModule
import com.mho.training.features.keywords.KeywordsFragmentComponent
import com.mho.training.features.keywords.KeywordsFragmentModule
import com.mho.training.features.login.LogInActivityComponent
import com.mho.training.features.login.LogInActivityModule
import com.mho.training.features.main.MainActivityComponent
import com.mho.training.features.main.MainActivityModule
import com.mho.training.features.moviedetail.MovieDetailActivityComponent
import com.mho.training.features.moviedetail.MovieDetailActivityModule
import com.mho.training.features.movieinfo.MovieInfoFragmentComponent
import com.mho.training.features.movieinfo.MovieInfoFragmentModule
import com.mho.training.features.persondetail.PersonDetailActivityComponent
import com.mho.training.features.persondetail.PersonDetailActivityModule
import com.mho.training.features.relatedmoviesbyperson.RelatedMoviesByPersonFragmentComponent
import com.mho.training.features.relatedmoviesbyperson.RelatedMoviesByPersonFragmentModule
import com.mho.training.features.reviews.di.ReviewsFragmentComponent
import com.mho.training.features.reviews.di.ReviewsFragmentModule
import com.mho.training.features.trailers.TrailersFragmentComponent
import com.mho.training.features.trailers.TrailersFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class,
    MovieRequestModule::class, RequestModule::class, ResourcesModule::class])
interface MyMoviesComponent {

    fun plus(module: CreditsFragmentModule): CreditsFragmentComponent
    fun plus(module: KeywordsFragmentModule): KeywordsFragmentComponent
    fun plus(module: LogInActivityModule): LogInActivityComponent
    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: MovieDetailActivityModule): MovieDetailActivityComponent
    fun plus(module: MovieInfoFragmentModule): MovieInfoFragmentComponent
    fun plus(module: PersonDetailActivityModule): PersonDetailActivityComponent
    fun plus(module: RelatedMoviesByPersonFragmentModule): RelatedMoviesByPersonFragmentComponent
    fun plus(module: ReviewsFragmentModule): ReviewsFragmentComponent
    fun plus(module: TrailersFragmentModule): TrailersFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}