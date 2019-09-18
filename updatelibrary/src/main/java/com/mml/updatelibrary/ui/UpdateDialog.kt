package com.mml.updatelibrary.ui

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.data.UpdateUrl

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-17 下午3:32
 * Description: This is UpdateDialog
 * Package: com.mml.updatelibrary.data
 * Project: OnceApplication
 */
class UpdateDialog {

    fun checkUpdate(){
        val httpAsync =  UpdateUrl("").url.httpGet().responseObject<UpdateInfo>{ request, response, result ->
               result.fold(success ={updateInfo->
                   Log.i("UpdateDialog.kt:21", "content:$updateInfo")
               },failure = {fuelError ->
                   Log.i("UpdateDialog.kt:21", "content:$fuelError")
               })
        }
        httpAsync.join()
    }
}