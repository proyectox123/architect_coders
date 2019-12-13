package com.mho.training.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviePopularRequest: MoviePopularRequest,
    private val movieTopRatedRequest: MovieTopRatedRequest
) :
    ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _model = MutableLiveData<MainUiModel>()
    val model: LiveData<MainUiModel>
        get() {
            return _model
        }

    private val _movieCategory = MutableLiveData<MovieCategoryEnum>()
    val movieCategory: LiveData<MovieCategoryEnum>
        get() {
            if (_movieCategory.value == null) {
                _movieCategory.value = MovieCategoryEnum.TOP_RATED
            }

            return _movieCategory
        }

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieListRefresh() {
        refresh(_movieCategory.value ?: MovieCategoryEnum.TOP_RATED)
    }

    fun onMovieCategory() = _movieCategory.value

    fun onMoviePopularListRefresh() {
        _movieCategory.value = MovieCategoryEnum.POPULAR
    }

    fun onMovieHighestRatedListRefresh() {
        _movieCategory.value = MovieCategoryEnum.TOP_RATED
    }

    fun onMovieFavoriteListRefresh() {
        _movieCategory.value = MovieCategoryEnum.FAVORITE
    }

    fun onMovieClicked(movie: MovieEntity) {
        _model.value = MainUiModel.Navigation(movie)
    }

    //endregion

    //region Private Methods

    private fun refresh(movieCategory: MovieCategoryEnum) {
        launch {
            _model.value = MainUiModel.Loading
            _model.value = when (movieCategory) {
                MovieCategoryEnum.TOP_RATED -> validateRequestedList(movieTopRatedRequest.requestMovieList())
                MovieCategoryEnum.POPULAR -> validateRequestedList(moviePopularRequest.requestMovieList())
                MovieCategoryEnum.FAVORITE -> validateRequestedList(null) //TODO Check from database
            }
        }
    }

    private fun validateRequestedList(movieList: List<MovieEntity>?) = if (movieList == null) {
        MainUiModel.Error
    } else {
        MainUiModel.Content(movieList)
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class MainUiModel {
        class Content(val movies: List<MovieEntity>) : MainUiModel() {
            override fun toString() = "Content movies size -> ${movies.size}"
        }

        class Navigation(val movie: MovieEntity) : MainUiModel() {
            override fun toString() = "Navigation movie -> ${movie.title}"
        }

        object Loading : MainUiModel() {
            override fun toString() = "Loading"
        }

        object Error : MainUiModel() {
            override fun toString() = "Error"
        }
    }

    //endregion
}