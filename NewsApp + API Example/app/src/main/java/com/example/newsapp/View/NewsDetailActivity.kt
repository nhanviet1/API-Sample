package com.example.newsapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.Model2.Article
import com.example.newsapp.View.adapter.NewsAdapter
import com.example.newsapp.Viewmodel.NewsViewmodel
import com.example.newsapp.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding
    private val viewModel: NewsViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        val title = intent.extras!!.getString("title")
        binding.tvTitle.text = title
        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.news2.observe(this) {
            it.forEach {
                if (it.title.lowercase() == title!!.lowercase()) {
                    Glide.with(this).load(it.urlToImage).into(binding.picture)
                    binding.content.text = it.content
                }
            }
        }
    }
}