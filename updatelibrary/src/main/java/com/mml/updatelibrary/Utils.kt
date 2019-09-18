package com.mml.updatelibrary

import android.util.Log

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-18 下午3:26
 * Description: This is Utils
 * Package: com.mml.updatelibrary
 * Project: OnceApplication
 */
fun Any.log(msg: String,tag:String="tag"){
    if (BuildConfig.DEBUG){
        Log.i(tag,msg)
    }
}