package com.example.android.usecases

import androidx.lifecycle.LiveData
import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie

class GetFavoriteMovieListWithChangesUseCase(private val movieRepository: MovieRepository){
    fun invoke(): LiveData<List<Movie>> =
        movieRepository.getFavoriteMovieListWithChanges()
}