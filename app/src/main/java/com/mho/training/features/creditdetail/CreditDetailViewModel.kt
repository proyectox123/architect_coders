package com.mho.training.features.creditdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Credit
import com.mho.training.utils.Event
import com.mho.training.utils.Scope

class CreditDetailViewModel(
    private val credit: Credit?
) : ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _infoCredit = MutableLiveData<Credit>()
    val infoCredit: LiveData<Credit> get() = _infoCredit

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
        Log.d(TAG, "onMovieInformation credit -> $credit")
        if(credit == null){
            _events.value = Event(Navigation.CloseActivity)
            return
        }

        _infoCredit.value = credit
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        object CloseActivity: Navigation()
    }

    //endregion

    companion object {

        private val TAG = CreditDetailViewModel::class.java.simpleName

    }

}