package com.example.newsapp.Datasource

import com.example.newsapp.Constant
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        const val RESPONSE_CODE_SUCCESS = 200
        const val RESPONSE_CODE_UNAUTHORIZED = 401
        private const val TIMEOUT_SECONDS = 60L
        private const val BASE_URL_1 = "https://newsapi.org/v2/"
        fun getRetrofitInstance(): NewsService {
            val baseUrl = BASE_URL_1
            return Retrofit.Builder()
                .client(generateOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsService::class.java)
        }

        private fun generateOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
//                if (chain.request().header(Constant.KEY_AUTHORIZATION).isNullOrEmpty()) {
                    request = request.newBuilder()
                        .addHeader(Constant.KEY_AUTHORIZATION, "1234")
                        .build()
//                }
                    try {
                        chain.proceed(request)
                    } catch (exception: Exception) {
                        return@addInterceptor Response.Builder()
                            .request(request)
                            .code(Constant.ERROR_CODE_NETWORK_PROBLEM)
                            .protocol(Protocol.HTTP_1_1)
                            .message("An error has occurred when processing request: ${exception.stackTraceToString()}")
                            .body(ResponseBody.create(null, "{${exception}}"))
                            .build()
                    }
                }.build()
        }
    }
}