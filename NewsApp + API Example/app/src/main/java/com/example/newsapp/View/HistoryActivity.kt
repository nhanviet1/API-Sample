package com.example.newsapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.Model2.Article
import com.example.newsapp.View.adapter.NewsAdapter
import com.example.newsapp.Viewmodel.NewsViewmodel
import com.example.newsapp.databinding.ActivityHistoryBinding
import com.example.newsapp.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: NewsViewmodel by viewModels()
    private lateinit var adapter: NewsAdapter
    private var newsList = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        val list= intent.extras!!.getStringArrayList("list")
        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.news2.observe(this) { abc ->
            abc.forEach {
                list?.forEach { title ->
                    if (title == it.title) {
                        newsList.add(it)
                        adapter = NewsAdapter(newsList, object : NewsAdapter.Callback {
                            override fun onClickItem(item: Article, position: Int) {
                                val intent = Intent(this@HistoryActivity, NewsDetailActivity::class.java)
                                intent.putExtra("title", item.title)
                                startActivity(intent)
                            }
                        })
                        binding.rvNews.layoutManager = LinearLayoutManager(this)
                        binding.rvNews.adapter = adapter
                    }
                }
            }
        }
    }
}