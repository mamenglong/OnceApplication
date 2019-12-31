package com.mml.onceapplication.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-19 上午9:46
 * Description: This is API
 * Package: com.m.l.tran.avatar.api
 * Project: TranAvatar
 */
data class IconItem(
    @SerializedName("url")
    var url:String="",
    @SerializedName("type")
    var type:String="",
    @SerializedName("name")
    var name:String="")
interface APIList{
    companion object{
        val BASE_URL: String
            get() = "https://www.easy-mock.com/mock/"
    }
    @GET("5dff194056438735d227bbe3/tran/img/list/")
    suspend fun getIconList():List<IconItem>
}

 
