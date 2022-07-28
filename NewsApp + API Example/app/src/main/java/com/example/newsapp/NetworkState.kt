package com.example.newsapp

class NetworkState(val status: Status, val message: String?) {
    constructor(status: Status) : this(status, "")

    companion object {
        val LOADING = NetworkState(Status.LOADING)
        val SUCCESS = NetworkState(Status.SUCCESSFUL)
        val ERROR = NetworkState(Status.FAILED)
        val END_OF_LIST = NetworkState(Status.END_OF_LIST)
    }

    enum class Status {
        LOADING,
        SUCCESSFUL,
        FAILED,
        END_OF_LIST
    }
}