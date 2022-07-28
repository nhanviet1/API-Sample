package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseDataSource(protected val compositeDisposable: CompositeDisposable) {
    protected val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState
}