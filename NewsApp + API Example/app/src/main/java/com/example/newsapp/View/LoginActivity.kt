package com.example.newsapp.View

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.newsapp.Datasource.FakeListLogin
import com.example.newsapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var checkLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView(){
        binding.LoginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.LoginButton2.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }


    //hidingKeyboard
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