package com.example.newsapp.View

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Model1.Article2
import com.example.newsapp.Model2.Article
import com.example.newsapp.View.adapter.Adapter2
import com.example.newsapp.View.adapter.NewsAdapter
import com.example.newsapp.Viewmodel.NewsViewmodel
import com.example.newsapp.databinding.ActivityLoginBinding
import com.example.newsapp.databinding.ActivityMain2Binding
import com.example.newsapp.gone
import com.example.newsapp.visible

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var adapter: Adapter2
    private val viewModel: NewsViewmodel by viewModels()
    private var newsList = ArrayList<Article2>()
    private var history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getNews2()
        checkNetwork()
        setupView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupView() {
        viewModel.news3.observe(this) {
            if (it != null) {
                Log.d("Lmeow News2", "${it.size}")
                newsList.addAll(it)
                adapter = Adapter2(newsList, object : Adapter2.Callback {
                    override fun onClickItem(item: Article2, position: Int) {
//                        val intent = Intent(this@MainActivity2, NewsDetailActivity::class.java)
//                        history.add(item.title)
//                        intent.putExtra("title", item.title)
//                        startActivity(intent)
                    }
                })
                binding.rvNews.layoutManager = LinearLayoutManager(this)
                binding.rvNews.adapter = adapter
            }
        }
        binding.buttonSearch.setOnClickListener {
            searchNews()
//            callHotline()
        }
        binding.history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("list", history)
            startActivity(intent)
        }
    }

    private fun checkNetwork() {
        viewModel.netWorkStatus.observe(this) {
            if (it == "Loading") {
                binding.progressBar2.visible()
            } else if (it == "Success") {
                binding.progressBar2.gone()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchNews() {
        newsList.clear()
        val searchTitle = binding.search.text.toString()
        viewModel.news3.observe(this) {
            if (searchTitle.isEmpty()) {
                newsList.addAll(it)
                adapter.notifyDataSetChanged()
            } else {
                it.forEach {
                    if (it.title.lowercase().contains(searchTitle.lowercase())) {
                        newsList.add(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun callHotline() {
        Log.d("Hotline", "HEHE 1")
        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Hotline", "HEHE 2")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1212
                )
            } else {
                Log.d("Hotline", "HEHE 3")
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:${1234}")
                startActivity(callIntent)
            }
        } catch (e: Exception) {
//            LogUtil.T(e.toString())
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

    protected fun hideKeyboardOnTouchOutside() = true
}