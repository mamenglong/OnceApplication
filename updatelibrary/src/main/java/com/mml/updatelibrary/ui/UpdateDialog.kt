package com.mml.updatelibrary.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject

import com.github.kittinunf.fuel.httpGet
import com.mml.updatelibrary.GlobalContextProvider
import com.mml.updatelibrary.R
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.data.UpdateUrl
import com.mml.updatelibrary.log
import com.mml.updatelibrary.service.UpdateService
import com.mml.updatelibrary.service.UpdateService.Companion.UPDATE_START_ACTION
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
                      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                          startForegroundService(Intent(this, UpdateService::class.java))
                      } else {
                          startService(Intent(this, UpdateService::class.java))
                      }
                    //  notification(this)
                  }

                }, failure = { fuelError ->
                    log(msg = "content:$fuelError", tag = "UpdateDialog")
                })
            }
        httpAsync.join()
    }

    fun sss() {
        val http = Fuel.download("http://elf.static.maibaapp.com/butterfly/pure/vc_examine.json")
            /*   .streamDestination { response, request ->
                   val file=File("config.json")
                  Pair(FileOutputStream(file),{FileInputStream(file)})

               }*/
            .fileDestination { response, url ->
                File.createTempFile("temp", ".json")
            }
            .response { result ->
                val aa = result.component1()!!.toString(Charset.defaultCharset())
                log(msg = "content:$aa", tag = "UpdateDialog")
            }
    }

    fun notification(context: Context) {
        val intent = Intent(UPDATE_START_ACTION)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //8.0   channelId
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default"
            val channelName = "通知"
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        //TaskStackBuilder
/*
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(null)
        stackBuilder.addNextIntent(intent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
*/

        val notification = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.ic_update_logo)
            .setContentTitle("这是个通知")
            .setContentText("通知")
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
        //    .setContentIntent(pendingIntent)
            .build()

        manager.notify(14, notification)
    }

}