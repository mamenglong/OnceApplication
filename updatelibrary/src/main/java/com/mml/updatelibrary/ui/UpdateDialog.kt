package com.mml.updatelibrary.ui

import android.content.Intent
import android.os.Environment
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject

import com.github.kittinunf.fuel.httpGet
import com.mml.updatelibrary.GlobalContextProvider
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.data.UpdateUrl
import com.mml.updatelibrary.log
import com.mml.updatelibrary.service.UpdateService
import com.mml.updatelibrary.service.UpdateService.Companion.ACTION_UPDATE_FAIL
import com.mml.updatelibrary.service.UpdateService.Companion.ACTION_UPDATE_PROCESS
import com.mml.updatelibrary.service.UpdateService.Companion.ACTION_UPDATE_SUCCESS
import java.io.File
import java.nio.charset.Charset

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-17 下午3:32
 * Description: This is UpdateDialog
 * Package: com.mml.updatelibrary.data
 * Project: OnceApplication
 */
class UpdateDialog {

    fun checkUpdate() {
        val httpAsync = UpdateUrl().url.httpGet()

            /*  .responseString { request, response, result ->
                  val input = response.body().toStream()
                  val string = input.bufferedReader().readLines().toString()
                  println(string)
                  log(msg = "content:${string}", tag = "UpdateDialog")
                  log(msg = "content:${result.component1()}", tag = "UpdateDialog")
              }*/
            .responseObject<UpdateInfo> { response, _, result ->
                log(msg = "content:${response.body}", tag = "UpdateDialog")
                result.fold(success = { updateInfo ->
                    log(msg = "content:$updateInfo", tag = "UpdateDialog")

                  GlobalContextProvider.getGlobalContext().apply {
                      UpdateService.start(this)
                    //  notification(this)
                  }

                }, failure = { fuelError ->
                    log(msg = "content:$fuelError", tag = "UpdateDialog")
                })
            }
        httpAsync.join()
    }

    fun sss() {
        val file = File(Environment.getExternalStorageDirectory(), "Auto/update.apk")
        if (!file.exists())
            file.createNewFile()
        GlobalContextProvider.getGlobalContext().apply {
            UpdateService.start(this)
        }
        val http = Fuel
            .download("https://ali-fir-pro-binary.fir.im/b725376798430078f69d0558131662c09b1f6a38.apk?auth_key=1569383235-0-0-e7c886fe18f51a517958451bdbb04f2c")
            .fileDestination { response, url ->
                file
            }
            .progress { readBytes, totalBytes ->
                val process=readBytes.toFloat() / totalBytes.toFloat()
                GlobalContextProvider.getGlobalContext().sendBroadcast(Intent(ACTION_UPDATE_PROCESS).apply {
                    log(msg = "readBytes:$readBytes  totalBytes:$totalBytes  process:${(process*100).toInt()}", tag = "UpdateDialog")
                    putExtra("process",(process*100).toInt())
                })
            }
            .response { result ->
                result.fold(
                    success = {
                        val aa = result.component1()!!.toString(Charset.defaultCharset())
                        log(msg = "content:$aa", tag = "UpdateDialog")
                        GlobalContextProvider.getGlobalContext().sendBroadcast(Intent(ACTION_UPDATE_SUCCESS))
                    },
                    failure = {
                        GlobalContextProvider.getGlobalContext().sendBroadcast(Intent(ACTION_UPDATE_FAIL))
                    }
                )

            }

    }
}