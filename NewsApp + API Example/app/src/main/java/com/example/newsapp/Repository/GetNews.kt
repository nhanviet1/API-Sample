package com.example.newsapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.Datasource.NewsService
import com.example.newsapp.Datasource.RetrofitInstance
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.Article
import com.example.newsapp.Model2.BodyModel

class GetNews {
    private val retrofitService = RetrofitInstance
        .getRetrofitInstance()
        .create(NewsService::class.java)

    suspend fun getNews(bodyModel: BodyModel) =
        retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)

}