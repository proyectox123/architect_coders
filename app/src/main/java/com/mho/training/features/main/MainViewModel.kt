package com.mho.training.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import com.mho.training.enums.MovieEnum
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val moviePopularRequest: MoviePopularRequest,
                    private val movieTopRatedRequest: MovieTopRatedRequest) :
    ViewModel(),
    Scope by Scope.Impl(){

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _model = MutableLiveData<MainUiModel>()
    val model: LiveData<MainUiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private var movieSortByType: MovieEnum = MovieEnum.TOP_RATED

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieListRefresh(){
        refresh()
    }

    fun onMoviePopularListRefresh(){
        movieSortByType = MovieEnum.POPULAR
        refresh()
    }

    fun onMovieHighestRatedListRefresh(){
        movieSortByType = MovieEnum.TOP_RATED
        refresh()
    }

    fun onMovieFavoriteListRefresh(){
        movieSortByType = MovieEnum.FAVORITE
        refresh()
    }

    fun onMovieClicked(movie: MovieEntity) {
        _model.value = MainUiModel.Navigation(movie)
    }

    //endregion

    //region Private Methods

    private fun refresh() {
        launch {
            _model.value = MainUiModel.Loading
            _model.value = when(movieSortByType){
                MovieEnum.TOP_RATED -> {
                    validateRequestedList(movieTopRatedRequest.requestMovieList())
                }
                MovieEnum.POPULAR -> {
                    validateRequestedList(moviePopularRequest.requestMovieList())
                }
                MovieEnum.FAVORITE -> { //TODO Check from database
                    validateRequestedList(null)
                }
            }
        }
    }

    private fun validateRequestedList(movieList: List<MovieEntity>?) = if (movieList == null){
        MainUiModel.Error
    } else {
        MainUiModel.Content(movieList)
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class MainUiModel {
        class Content(val movies: List<MovieEntity>): MainUiModel()
        class Navigation(val movie: MovieEntity): MainUiModel()
        object Loading: MainUiModel()
        object Error: MainUiModel()
    }

    //endregion
}