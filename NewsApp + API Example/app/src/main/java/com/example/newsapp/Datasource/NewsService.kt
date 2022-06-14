package com.example.newsapp.Datasource

import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.Article
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface NewsService {
    companion object{
        const val API_NEWS = "top-headlines"
    }
    @GET(API_NEWS)
   suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<ABC>
}

