package com.mho.training.features.relatedmoviesbyperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.mho.training.mviandroid.bases.BaseViewModel
import com.mho.training.utils.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class RelatedMoviesByPersonViewModel(
    private val personId: Int,
    private val getMovieListByPersonUseCase: GetMovieListByPersonUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _hasNotMovies = MutableLiveData<Boolean>()
    val hasNotMovies: LiveData<Boolean> get() = _hasNotMovies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

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

    fun onRelatedMoviesByPerson(){
        launch {
            _loading.value = true
            validateMovieResult(getMovieListByPersonUseCase.invoke(personId))
            _loading.value = false
        }
    }

    fun onMovieClicked(movie: Movie) {
        _events.value = Event(Navigation.NavigateToMovie(movie))
    }

    //endregion

    //region Private Methods

    private fun validateMovieResult(movieListResult: DataResult<List<Movie>>){
        when(movieListResult){
            is DataResult.Success -> {
                _movies.value = movieListResult.data
                _hasNotMovies.value = false
            }
            is DataResult.Error -> {
                _movies.value = mutableListOf()
                _hasNotMovies.value = true
            }
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        data class NavigateToMovie(val movie: Movie): Navigation()
    }

    //endregion
}