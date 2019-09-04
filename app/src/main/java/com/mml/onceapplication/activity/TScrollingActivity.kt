package com.mml.onceapplication.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tscrolling.*
import com.google.gson.reflect.TypeToken



class TScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mml.onceapplication.R.layout.activity_tscrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }


/*class TE<T>{
    var us:T
    var user:T
    get() =JsonUtils.fromJson<T>("{name:\"meng\"}", T::class)
}*/


}
//    implementation 'com.google.code.gson:gson:2.8.5'
data class User(var name:String,var msg:String)
data class ResponseResult<T>(var code:Int, var msg:String, var data:T)
fun main(args: Array<String>) {
    val json="{code:1,msg:\"success\",data:{name:\"long\"}}"
    val userType = object : TypeToken<ResponseResult<User>>() {}.type
    val ss= Gson().fromJson<ResponseResult<User>>(json,userType)
    println(ss.toString())

    println(Gson().toJson(Test<ResponseResult<User>>().result))
}

class Test<T>{
    var json="{code:1,msg:\"success\",data:{name:\"long\"}}"
    val result:T
    get() {
        val userType = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(json,userType)
    }
}
