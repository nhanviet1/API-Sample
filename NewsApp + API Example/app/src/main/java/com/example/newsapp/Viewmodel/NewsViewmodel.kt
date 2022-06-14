package com.example.newsapp.Viewmodel

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
        getNews()
        _news2.addSource(_news){
            _news2.value = it
        }
    }

    fun getNews() {
        viewModelScope.launch {
            try {
                val a = getNewsRepository.getNews(BodyModel())
               _news.value = a.body()!!.articles
                netWorkStatus.value = "Success"
            } catch (e: Exception) {
                _news.value = listOf()
            }
        }
    }


}