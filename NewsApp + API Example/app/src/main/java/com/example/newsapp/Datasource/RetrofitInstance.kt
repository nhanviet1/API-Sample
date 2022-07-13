package com.example.newsapp.Datasource

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val RESPONSE_CODE_SUCCESS = 200
        const val RESPONSE_CODE_UNAUTHORIZED = 401
        private const val TIMEOUT_SECONDS = 60L
        private const val BASE_URL = "https://newsapi.org/v2/"
        fun getRetrofitInstance(): NewsService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(NewsService::class.java)
        }
    }
}