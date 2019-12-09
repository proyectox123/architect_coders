package com.mho.training.features.main

import androidx.lifecycle.ViewModel
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MovieListTask
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), Scope by Scope.Impl(){

    private lateinit var navigator: MainNavigator

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun setNavigator(navigator: MainNavigator){
        this.navigator = navigator
    }

    fun onMovieListRefresh(){
        launch {
            validateList(MovieListTask().requestPopularMovieList())
        }
    }

    private fun validateList(movieList: List<MovieEntity>?){
        if(movieList == null){
            navigator.showUpdateMovieListError()
            return
        }

        navigator.updateMovieList(movieList)
    }
}

interface MainNavigator{
    fun showUpdateMovieListError()
    fun updateMovieList(movieList: List<MovieEntity>)
}