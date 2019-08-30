package com.mml.onceapplication

import android.os.Looper
import android.util.Log
import android.widget.Toast


fun showToast(msg:String)= run {
    val looper=Looper.getMainLooper()
    if (looper== Looper.myLooper()){
            Toast.makeText(OnceApplication.instances,msg,Toast.LENGTH_SHORT).show()
    }
}

fun Any.log(msg: String,tag:String="tag"){
    if (BuildConfig.DEBUG){
        Log.i(tag,msg)
    }
}

class Ext {

}