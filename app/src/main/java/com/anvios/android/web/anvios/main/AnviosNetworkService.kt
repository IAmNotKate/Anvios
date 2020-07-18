package com.anvios.android.web.anvios.main

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://127.0.0.1:8080"

class AnviosNetworkService {
    private val api: AnviosApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        api = retrofit.create(AnviosApi::class.java)
    }

    fun getApi() = api

    companion object {
        @JvmStatic
        private var INSTANCE: AnviosNetworkService? = null

        @JvmStatic
        fun getInstance(): AnviosNetworkService {
            if (INSTANCE == null) INSTANCE = AnviosNetworkService()
            return INSTANCE!!
        }
    }
}