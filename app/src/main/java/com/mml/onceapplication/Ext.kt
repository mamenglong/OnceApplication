package com.mml.onceapplication

import android.os.Looper
import android.widget.Toast


fun showToast(msg:String)= run {
    val looper=Looper.getMainLooper()
    if (looper== Looper.myLooper()){
            Toast.makeText(OnceApplication.instances,msg,Toast.LENGTH_SHORT).show()
    }
}


class Ext {

}