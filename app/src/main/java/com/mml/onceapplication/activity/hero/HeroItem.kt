package com.mml.onceapplication.activity.hero

import com.google.gson.annotations.SerializedName

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-31 下午5:33
 * Description: This is HeroItem
 * Package: com.mml.onceapplication.activity.hero
 * Project: OnceApplication
 */
//"ename": 105,
//"cname": "廉颇",
//"title": "正义爆轰",
//"new_type": 0,
//"hero_type": 3,
//"skin_name": "正义爆轰|地狱岩魂"
data class HeroItem(
    @SerializedName("ename")
    var ename:Int = 0,
    @SerializedName("cname")
    var cname:String="",
    @SerializedName("title")
    var title:String = "",
    @SerializedName("new_type")
    var new_type:Int=0,
    @SerializedName("hero_type")
    var hero_type:Int=0,
    @SerializedName("skin_name")
    var skin_name:String="",
    @SerializedName("skin_info")
    var skin_info:MutableList<SkinInfo> = mutableListOf()

)
data class SkinInfo(
    @SerializedName("url")
    var url:String= "",
    @SerializedName("name")
    var name:String=""
)