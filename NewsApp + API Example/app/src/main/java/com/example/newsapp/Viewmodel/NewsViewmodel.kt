package com.example.newsapp.Viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.Article
import com.example.newsapp.Model2.BodyModel
import com.example.newsapp.Repository.GetNews
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class NewsViewmodel() : ViewModel() {
    private val getNewsRepository = GetNews()
    var netWorkStatus = MutableLiveData<String>("Loading")
    private var _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news
    private var _news2 = MediatorLiveData<List<Article>>()
    val news2: LiveData<List<Article>> get() = _news2

    init {
        _news2.addSource(_news){
            _news2.value = it
        }
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            try {
                val a = getNewsRepository.getNews(BodyModel())
                Log.d("Lmeow", "${a.value}")
               _news.value = a.value!!.articles
                netWorkStatus.value = "Success"
            } catch (e: Exception) {
                _news.value = listOf()
            }
        }
    }


}