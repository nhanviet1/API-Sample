package com.example.newsapp

import com.example.newsapp.Datasource.RetrofitInstance
import com.example.newsapp.Model2.ErrorResponse
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

object ResponseParser {
    private val gson = GsonBuilder().create()

    fun <T : Any> parseObject(response: Response<ResponseBody>?, classOfT: Class<T>): Any? {
        if (response == null) {
            return null
        } else {
            if (response.code() == RetrofitInstance.RESPONSE_CODE_SUCCESS &&
                response.body() != null
            ) {
                val result: T
                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    result = gson.fromJson(jsonObject.toString(), classOfT)
                } catch (e: Exception) {
                    return ErrorResponse(ErrorResponse.Data(e.message!!))
                }
                return result
            } else if (response.code() == RetrofitInstance.RESPONSE_CODE_UNAUTHORIZED) {
                return ErrorResponse(ErrorResponse.Data(ErrorMessage.TOKEN_HAS_EXPIRED, ""))
            } else if (response.errorBody() != null) {
                val error: ErrorResponse

                try {
                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    error = gson.fromJson(jsonObject.toString(), ErrorResponse::class.java)
                } catch (e: JSONException) {
                    return ErrorResponse(ErrorResponse.Data())
                } catch (e: IOException) {
                    return ErrorResponse()
                }
                return error
            } else {
                return ErrorResponse()
            }
        }
    }

    fun <T : Any> parseJsonString(jsonString: String, classOfT: Class<T>): Any? {
        val result: T
        try {
            val jsonObject = JSONObject(jsonString)
            result = gson.fromJson(jsonObject.toString(), classOfT)
        } catch (e: Exception) {
            return null
        }
        return result
    }
}