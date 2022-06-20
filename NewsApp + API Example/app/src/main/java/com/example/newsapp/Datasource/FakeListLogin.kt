package com.example.newsapp.Datasource

import com.example.newsapp.Model2.LoginModel

class FakeListLogin {
    fun getData(): List<LoginModel> {
        return listOf(
            LoginModel("Lmeow", "123456"),
            LoginModel("Lmeow1", "ChonkyCat"),
            LoginModel("Lmeow2", "123456"),
            LoginModel("Lmeow3", "123456"),
            LoginModel("Duc123", "123123")
        )
    }
}