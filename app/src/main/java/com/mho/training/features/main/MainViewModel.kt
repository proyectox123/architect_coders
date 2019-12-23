package com.mho.training.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.utils.Event
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

    /*
    private val _model = MutableLiveData<MainUiModel>()
    val model: LiveData<MainUiModel>
        get() {
            return _model
        }

     */

    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToMovie = MutableLiveData<Event<Int>>()
    val navigateToMovie: LiveData<Event<Int>> get() = _navigateToMovie

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

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
        _navigateToMovie.value = Event(movie.id)
    }

    //endregion

    //region Private Methods

    private fun refresh(movieCategory: MovieCategoryEnum) {
        launch {
            _loading.value = true
            _error.value = false

            val movieList: List<MovieEntity>? = when (movieCategory) {
                MovieCategoryEnum.TOP_RATED -> movieTopRatedRequest.requestMovieList()
                MovieCategoryEnum.POPULAR -> moviePopularRequest.requestMovieList()
                MovieCategoryEnum.FAVORITE -> null //TODO Check from database
            }

            _error.value = movieList.isNullOrEmpty()
            _movies.value = movieList

            _loading.value = false
        }
    }

    /*
    private fun validateRequestedList(movieList: List<MovieEntity>?){
        if (movieList == null) {
            MainUiModel.Error
        } else {
            MainUiModel.Content(movieList)
        }
    }
    */

    //endregion

    //region Inner Classes & Interfaces

    /*
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

     */

    //endregion
}