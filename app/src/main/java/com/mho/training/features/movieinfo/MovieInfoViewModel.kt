package com.mho.training.features.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetMovieDetailByIdUseCase
import com.mho.training.mviandroid.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieInfoViewModel(
    private val movie: Movie,
    private val getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _infoMovie = MutableLiveData<Movie>()
    val infoMovie: LiveData<Movie> get() = _infoMovie

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail

    //endregion

    //region Public Methods

    fun onMovieInformation(){
        _infoMovie.value = movie

        launch {
            validateMovieDetailResult(getMovieDetailByIdUseCase.invoke(movie.id))
        }
    }

    //endregion

    //region Private Methods

    private fun validateMovieDetailResult(movieDetailResult: DataResult<MovieDetail>){
        when(movieDetailResult){
            is DataResult.Success -> {
                _movieDetail.value = movieDetailResult.data
            }
            is DataResult.Error -> {
                _movieDetail.value = null
            }
        }
    }

    //endregion
}