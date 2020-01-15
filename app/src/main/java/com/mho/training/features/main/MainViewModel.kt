package com.mho.training.features.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.usecases.*
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase,
    private val getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase,
    private val getPopularMovieListUseCase: GetPopularMovieListUseCase,
    private val getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase,
    private val getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase
) :
    ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    val favoriteMovies: LiveData<List<Movie>> get() = getFavoriteMovieListWithChangesUseCase.invoke().asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _movieCategory = MutableLiveData<MovieCategoryEnum>()
    val movieCategory: LiveData<MovieCategoryEnum>
        get() {
            if (_movieCategory.value == null) {
                _movieCategory.value = MovieCategoryEnum.IN_THEATERS
            }

            return _movieCategory
        }

    private val _events = MutableLiveData<Event<Navigation>>()
    val events: LiveData<Event<Navigation>> get() = _events

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieListRefresh() {
        _events.value = Event(Navigation.RequestLocationPermission)
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

    fun onMovieInTheatersListRefresh(){
        _movieCategory.value = MovieCategoryEnum.IN_THEATERS
    }

    fun onMovieFavoriteListUpdate(favoriteMovieList: List<Movie>){
        Log.d(TAG, "onMovieFavoriteListUpdate movieCategory -> ${_movieCategory.value}")
        Log.d(TAG, "onMovieFavoriteListUpdate favoriteMovieList -> ${favoriteMovieList.size}")
        if(_movieCategory.value == MovieCategoryEnum.FAVORITE){
            _error.value = favoriteMovieList.isNullOrEmpty()
            _movies.value = favoriteMovieList
        }
    }

    fun onMovieClicked(movie: Movie) {
        _events.value = Event(Navigation.NavigateToMovie(movie))
    }

    fun onCoarsePermissionRequested(hasPermission: Boolean) {
        Log.d(TAG, "onCoarsePermissionRequested hasPermission -> $hasPermission")
        if(hasPermission){
            refresh(_movieCategory.value ?: MovieCategoryEnum.TOP_RATED)
            return
        }

        _loading.value = false
        _error.value = true
    }

    //endregion

    //region Private Methods

    private fun refresh(movieCategory: MovieCategoryEnum){
        Log.d(TAG, "refresh movieCategory -> $movieCategory")
        launch {
            _loading.value = true
            _error.value = false

            when (movieCategory) {
                MovieCategoryEnum.FAVORITE -> validateMovieResult(getFavoriteMovieListUseCase.invoke())
                MovieCategoryEnum.IN_THEATERS -> validateMovieResult(getInTheatersMovieListUseCase.invoke())
                MovieCategoryEnum.POPULAR -> validateMovieResult(getPopularMovieListUseCase.invoke())
                MovieCategoryEnum.TOP_RATED -> validateMovieResult(getTopRatedMovieListUseCase.invoke())
            }

            _loading.value = false
        }
    }

    private fun validateMovieResult(movieListResult: DataResult<List<Movie>>){
        when(movieListResult){
            is DataResult.Success -> {
                _error.value = false
                _movies.value = movieListResult.data
            }
            is DataResult.Error -> {
                _error.value = true
                _movies.value = mutableListOf()
            }
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        class NavigateToMovie(val movie: Movie): Navigation()
        object RequestLocationPermission: Navigation()
    }

    //endregion

    companion object {

        private val TAG = MainViewModel::class.java.simpleName

    }
}