package com.example.newsapp.Model1

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Body(
    @SerializedName("q")
    var q: String = "tesla",
    @SerializedName("from")
    var from: String = "2022-06-28",
    @SerializedName("sortBy")
    var sortBy: String = "publishedAt",
    @SerializedName("apiKey")
    var apiKey: String= "198a7de09b314416a3a1e91d95ac77e6",
)