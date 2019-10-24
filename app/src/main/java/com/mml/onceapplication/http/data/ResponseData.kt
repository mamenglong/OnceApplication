package com.mml.onceapplication.http.data

data class ResponseData<out T>(val errorCode: Int, val errorMsg: String, val data: T)