package com.mml.onceapplication.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object API {

    val BASE_URL =  "https://wanandroid.com/"

    val reqApi by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return@lazy retrofit.create(RequestService::class.java)
    }

}