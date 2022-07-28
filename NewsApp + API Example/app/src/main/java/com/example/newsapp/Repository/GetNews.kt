package com.example.newsapp.Repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.BaseDataSource
import com.example.newsapp.Datasource.RetrofitInstance
import com.example.newsapp.Model1.Body
import com.example.newsapp.Model1.XYZ
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.BodyModel
import com.example.newsapp.NetworkState
import com.example.newsapp.ResponseParser
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetNews(compositeDisposable: CompositeDisposable) : BaseDataSource(compositeDisposable) {
    private val retrofitService = RetrofitInstance.getRetrofitInstance()

    suspend fun getNews(bodyModel: BodyModel): LiveData<ABC> {
        var result = MutableLiveData<ABC>()
        val data = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
        result.value = data.body()!!
        return result
    }

//    suspend fun getNewsX(bodyModel: BodyModel): LiveData<ABC> {
//        var result = MutableLiveData<ABC>()
//        val data = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
//        result.postValue(data.body())
//        return result
//    }

    fun getNews2(bodyModel: BodyModel): LiveData<ABC> {
        var result = MutableLiveData<ABC>()
        compositeDisposable.add(
            retrofitService.getNewsTest(bodyModel.country, bodyModel.category, bodyModel.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{_networkState.postValue(NetworkState.LOADING)}
                .subscribe({
                    val data =
                        ResponseParser.parseObject(it, ABC::class.java)
                    if (data is ABC) {
                        result.value = data!!
                        _networkState.value = NetworkState.SUCCESS
                    } else {
                        _networkState.value = NetworkState.ERROR
                    }
                }, {
                    _networkState.value = NetworkState.ERROR
                })
        )
        return result
    }

    suspend fun getNewsX(body: Body): LiveData<XYZ> {
        var result = MutableLiveData<XYZ>()
        val data = retrofitService.getNews2(body.q, body.from, body.sortBy, body.apiKey)
        result.value = data.body()!!
        return result
    }

}