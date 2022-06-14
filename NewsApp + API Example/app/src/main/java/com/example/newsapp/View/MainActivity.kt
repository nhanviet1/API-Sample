package com.example.newsapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Model2.Article
import com.example.newsapp.View.adapter.NewsAdapter
import com.example.newsapp.Viewmodel.NewsViewmodel
import com.example.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private val viewModel: NewsViewmodel by viewModels()
    private var newsList = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetwork()
        setupView()
    }

    private fun setupView() {
        viewModel.news2.observe(this) {
            newsList.addAll(it)
            Log.d("nhan", "${newsList.size}")
            adapter = NewsAdapter(newsList, object : NewsAdapter.Callback {
                override fun onClickItem(item: Article, position: Int) {
                    val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
                    intent.putExtra("title", item.title)
                    startActivity(intent)
                }
            })
            binding.rvNews.layoutManager = LinearLayoutManager(this)
            binding.rvNews.adapter = adapter
        }
    }

    private fun checkNetwork(){
        viewModel.netWorkStatus.observe(this){
            if (it == "Loading"){
                Log.d("nhan", "Hehe")
                binding.root.isGone
            } else if (it == "Success"){
                Log.d("nhan", "Hehe 2")
                binding.root.isVisible
            }
        }
    }

}