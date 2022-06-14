package com.example.newsapp.Model2

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BodyModel(
    @SerializedName("country")
    var country: String = "us",
    @SerializedName("category")
    var category: String = "business",
    @SerializedName("apiKey")
    var apiKey: String= "198a7de09b314416a3a1e91d95ac77e6",
): Serializable