package com.example.newsapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.Datasource.NewsService
import com.example.newsapp.Datasource.RetrofitInstance
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.Article
import com.example.newsapp.Model2.BodyModel
import com.example.newsapp.ResponseParser
import retrofit2.Response

class GetNews {
    private val retrofitService = RetrofitInstance.getRetrofitInstance()

    suspend fun getNews(bodyModel: BodyModel): LiveData<ABC> {
        var result = MutableLiveData<ABC>()
        val data = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
        val abc = data.isSuccessful
        Log.d("Lmeow", "abc: ${abc}")
        val xyz = data.raw()
        Log.d("Lmeow", "XYZ: ${xyz}")
        result.value = data.body()!!
        Log.d("Lmeow", "X ${result}")
        return result
    }

//    suspend fun getNews2(bodyModel: BodyModel): LiveData<ABC> {
//        var result = MutableLiveData<ABC>()
//        val abc = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
//        val data = ResponseParser.parseObject(abc, ABC::class.java)
//        result.value = (data as ABC?)!!
//        return result
//    }

}