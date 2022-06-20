package com.example.newsapp.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
    private var history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("ABC", "Hehe")
        checkNetwork()
        setupView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupView() {

        viewModel.news.observe(this) {
            newsList.addAll(it)

            adapter = NewsAdapter(newsList, object : NewsAdapter.Callback {
                override fun onClickItem(item: Article, position: Int) {
                    val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
                    history.add(item.title)
                    intent.putExtra("title", item.title)
                    startActivity(intent)
                }
            })
            binding.rvNews.layoutManager = LinearLayoutManager(this)

            binding.rvNews.adapter = adapter
        }
        binding.buttonSearch.setOnClickListener {
            searchNews()
        }
        binding.history.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            intent.putExtra("list", history)
            startActivity(intent)
        }
    }

    private fun checkNetwork(){
        viewModel.netWorkStatus.observe(this){
            if (it == "Loading"){
                binding.root.isGone
            } else if (it == "Success"){
                binding.root.isVisible
            }
        }
    }

    private fun searchNews(){
        newsList.clear()
        val searchTitle = binding.search.text.toString()
        viewModel.news.observe(this){
            if (searchTitle.isEmpty()){
                newsList.addAll(it)
                //notifyDataSetChanged() -> thông báo cho adapter dữ liệu đầu vào thay đổi
                adapter.notifyDataSetChanged()
            } else {
                it.forEach{
                    if (it.title.lowercase().contains(searchTitle.lowercase())){
                        newsList.add(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }



//Ẩn bàn phím
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (hideKeyboardOnTouchOutside() && event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    protected open fun hideKeyboardOnTouchOutside() = true

}