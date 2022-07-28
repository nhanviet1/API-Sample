package com.example.newsapp.Datasource

import com.example.newsapp.Model1.XYZ
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.Article
import com.example.newsapp.Model2.BodyModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.QueryMap

interface NewsService {
    companion object {
        const val API_NEWS = "top-headlines"
        const val API_NEWS_2 = "everything"
    }

    @GET(API_NEWS)
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<ABC>

//    @GET(API_NEWS)
//    suspend fun getNews(@QueryMap body: BodyModel): Response<ABC>

    @GET(API_NEWS)
    fun getNewsTest(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Single<Response<ResponseBody>>

    @GET(API_NEWS_2)
    suspend fun getNews2(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<XYZ>
}

