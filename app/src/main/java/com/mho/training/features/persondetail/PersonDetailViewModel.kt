package com.mho.training.features.persondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Movie
import com.example.android.domain.Person
import com.example.android.framework.data.remote.requests.Result
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.example.android.usecases.GetPersonInformationUseCase
import com.mho.training.utils.Constants.LESS_LINES_BIOGRAPHY
import com.mho.training.utils.Constants.MAX_LINES_BIOGRAPHY
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val creditId: Int,
    private val getPersonInformationUseCase: GetPersonInformationUseCase,
    private val getMovieListByPersonUseCase: GetMovieListByPersonUseCase
) : ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _infoPerson = MutableLiveData<Person>()
    val infoPerson: LiveData<Person> get() = _infoPerson

    private val _loadingPerson = MutableLiveData<Boolean>()
    val loadingPerson: LiveData<Boolean> get() = _loadingPerson

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _hasNotMovies = MutableLiveData<Boolean>()
    val hasNotMovies: LiveData<Boolean> get() = _hasNotMovies

    private val _hasPersonInformation = MutableLiveData<Boolean>()
    val hasPersonInformation: LiveData<Boolean> get() = _hasPersonInformation

    private val _showMoreBiography = MutableLiveData<Boolean>()
    val showMoreBiography: LiveData<Boolean> get() {
        if(_showMoreBiography.value == null){
            _showMoreBiography.value = true
        }

        return _showMoreBiography
    }

    private val _maxLines = MutableLiveData<Int>()
    val maxLines: LiveData<Int> get() {
        if(_maxLines.value == null){
            _maxLines.value = LESS_LINES_BIOGRAPHY
        }

        return _maxLines
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

    fun onCreditInformation(){
        launch {
            _loadingPerson.value = true
            validatePersonResult(getPersonInformationUseCase.invoke(creditId))
            _loadingPerson.value = false

            validateMovieResult(getMovieListByPersonUseCase.invoke(creditId))
        }
    }

    fun onMovieClicked(movie: Movie) {
        _events.value = Event(Navigation.NavigateToMovie(movie))
    }

    fun showMoreBiography(){
        _showMoreBiography.value = false
        _maxLines.value = MAX_LINES_BIOGRAPHY
    }

    fun showLessBiography(){
        _showMoreBiography.value = true
        _maxLines.value = LESS_LINES_BIOGRAPHY
    }

    //endregion

    //region Private Methods

    private fun validatePersonResult(personResult: Result<Person>){
        when(personResult){
            is Result.Success -> {
                _hasPersonInformation.value = true
                _infoPerson.value = personResult.data
            }
            is Result.Error -> {
                Log.d(TAG, "validateMovieResult error message -> ${personResult.exception.message}")
                _hasPersonInformation.value = false
                _events.value = Event(Navigation.CloseActivity)
            }
        }
    }

    private fun validateMovieResult(movieListResult: Result<List<Movie>>){
        when(movieListResult){
            is Result.Success -> {
                _movies.value = movieListResult.data
                _hasNotMovies.value = false
            }
            is Result.Error -> {
                Log.d(TAG, "validateMovieResult error message -> ${movieListResult.exception.message}")
                _movies.value = mutableListOf()
                _hasNotMovies.value = true
            }
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        class NavigateToMovie(val movie: Movie): Navigation()
        object CloseActivity: Navigation()
    }

    //endregion

    companion object {

        private val TAG = PersonDetailViewModel::class.java.simpleName

    }

}