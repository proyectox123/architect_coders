package com.mho.training.features.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Movie
import com.example.android.usecases.*
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase,
    getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase,
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

    private val _favoriteMovies = getFavoriteMovieListWithChangesUseCase.invoke()
    val favoriteMovies: LiveData<List<Movie>> get() = _favoriteMovies

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

            val movieList: List<Movie>? = when (movieCategory) {
                MovieCategoryEnum.TOP_RATED -> getTopRatedMovieListUseCase.invoke()
                MovieCategoryEnum.POPULAR -> getPopularMovieListUseCase.invoke()
                MovieCategoryEnum.FAVORITE -> getFavoriteMovieListUseCase.invoke()
                MovieCategoryEnum.IN_THEATERS -> getInTheatersMovieListUseCase.invoke()
            }

            _error.value = movieList.isNullOrEmpty()
            _movies.value = movieList

            Log.d(TAG, "refresh movies -> ${_movies.value?.size ?: 0}")

            _loading.value = false
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