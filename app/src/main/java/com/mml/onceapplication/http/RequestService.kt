package com.mml.onceapplication.http

import com.mml.onceapplication.http.data.Data
import com.mml.onceapplication.http.data.ResponseData
import retrofit2.http.GET

interface RequestService {
    @GET("wxarticle/chapters/json")
   suspend fun getDatas() : ResponseData<List<Data>>
}