package com.mml.updatelibrary.service

import android.app.*
import android.content.*
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.mml.updatelibrary.*
import com.mml.updatelibrary.GlobalContextProvider
import java.io.File
import java.nio.charset.Charset

class UpdateService : Service() {
    companion object {

        const val ACTION_UPDATE_START = "com.mml.updatelibrary.service.action.update_start"
        const val ACTION_UPDATE_FAIL = "com.mml.updatelibrary.service.action.update_fail"
        const val ACTION_UPDATE_SUCCESS = "com.mml.updatelibrary.service.action.update_success"
        const val ACTION_UPDATE_RETRY = "com.mml.updatelibrary.service.action.update_retry"
        const val ACTION_UPDATE_PROCESS = "com.mml.updatelibrary.service.action.update_process"
        const val ACTION_UPDATE_CANCEL = "com.mml.updatelibrary.service.action.update_cancel"
        const val ACTION_UPDATE_PAUSE = "com.mml.updatelibrary.service.action.update_pause"
        const val ACTION_UPDATE_INSTALL = "com.mml.updatelibrary.service.action.update_install"
        const val NotificationChannelID = "appUpdate"
        const val NOTIFICATION_ID = 1

        @JvmStatic
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                context.startForegroundService(Intent(context, UpdateService::class.java))
            } else {
                context.startService(Intent(context, UpdateService::class.java))
            }
        }
    }
    private val fileName="update.apk"
    private var http:CancellableRequest?=null
    private lateinit var notificationCompatBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var pauseAction: NotificationCompat.Action
    private lateinit var cancelAction: NotificationCompat.Action
    private lateinit var reTryAction: NotificationCompat.Action
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    ACTION_UPDATE_START -> {
                        log(ACTION_UPDATE_START, tag = "UpdateService")
                        showNotification()
                        getUpdateFile()
                    }
                    ACTION_UPDATE_PROCESS -> {
                        log(ACTION_UPDATE_PROCESS, tag = "UpdateService")
                        val process = it.getIntExtra("process", -1)
                        log("ACTION_UPDATE_PROCESS:$process", tag = "UpdateService")
                        updateNotificationProcess(process)
                    }
                    ACTION_UPDATE_CANCEL -> {
                        log(ACTION_UPDATE_CANCEL, tag = "UpdateService")
                        cancelNotification()
                        http?.cancel()
                    }
                    ACTION_UPDATE_FAIL -> {
                        log(ACTION_UPDATE_FAIL, tag = "UpdateService")
                        updateNotificationProcessContentToDownLoadFail()
                        http?.cancel()
                    }
                    ACTION_UPDATE_SUCCESS -> {
                        log(ACTION_UPDATE_SUCCESS, tag = "UpdateService")
                        updateNotificationProcessContentToDownLoadSuccess()
                    }
                    ACTION_UPDATE_RETRY -> {
                        log(ACTION_UPDATE_RETRY, tag = "UpdateService")
                        updateNotificationProcessContentToDownLoadRetry()
                        getUpdateFile()
                    }
                    ACTION_UPDATE_INSTALL -> {
                        log(ACTION_UPDATE_INSTALL, tag = "UpdateService")
                        Utils.installApk(
                            GlobalContextProvider.getGlobalContext(),
                            File(getDir("update", Context.MODE_PRIVATE), "update.apk")
                        )
                    }
                    ACTION_UPDATE_PAUSE -> {
                        log(ACTION_UPDATE_PAUSE, tag = "UpdateService")
                    }
                    else->{}
                }
            }
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        log("update service is create.", tag = "UpdateService")
        initNotification()
        registerReceiver()
        sendBroadcast(Intent(ACTION_UPDATE_START))
        
    }

    fun getUpdateFile(){
        ///data/data/包名/files
        filesDir
        cacheDir
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataDir
        }
        obbDir
        externalCacheDir
        val file = getDir("update", Context.MODE_PRIVATE)
        if (!file.exists()){
            file.mkdirs()
        }
        val update=File(file,fileName)
        if (!update.exists())
            update.createNewFile()
         http = Fuel
            .download("http://dl-ks.coolapkmarket.com/down/apk_upload/2019/1013/2be56956130b91169fd245364df18fe0-245443-o_1dn25652i1p72159lp73n0c1tscq-uid-1463983.apk?t=1571023424&k=c386eaa7a96d885cc44c97757e67438c")
            .fileDestination { response, url ->
                update
            }
            .progress { readBytes, totalBytes ->
                val process = readBytes.toFloat() / totalBytes.toFloat()
                GlobalContextProvider.getGlobalContext()
                    .sendBroadcast(Intent(ACTION_UPDATE_PROCESS).apply {
                        log(
                            msg = "readBytes:$readBytes  totalBytes:$totalBytes  process:${(process * 100).toInt()}",
                            tag = "UpdateService"
                        )
                        putExtra("process", (process * 100).toInt())
                    })
            }
            .response { result ->
                result.fold(
                    success = {
                        val aa = result.component1()!!.toString(Charset.defaultCharset())
                        log(msg = "content:$aa", tag = "UpdateService")
                        GlobalContextProvider.getGlobalContext()
                            .sendBroadcast(Intent(ACTION_UPDATE_SUCCESS))
                    },
                    failure = {
                        log(msg = "content:${it.cause}", tag = "UpdateService")
                        GlobalContextProvider.getGlobalContext()
                            .sendBroadcast(Intent(ACTION_UPDATE_FAIL))
                    }
                )

            }
        http?.join()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("update service   onStartCommand.", tag = "UpdateService")
        return START_STICKY_COMPATIBILITY// super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver()
    }

    private fun initNotification() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationCompatBuilder =
            NotificationCompat.Builder(applicationContext, NotificationChannelID)
                .setContentTitle(
                    applicationContext.getString(
                        R.string.is_updating
                    )
                )
                .setSmallIcon(R.drawable.ic_update_logo)
                .setContentText(applicationContext.getString(R.string.update_process, "0%"))
                .setProgress(100, 0, false)
                .setColor(-0x1dea20)
                .setTicker("开始下载...")
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setSubText("更新下载中")
                .setOngoing(true)    //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知

        pauseAction = NotificationCompat.Action(
            R.drawable.ic_noti_action_pause,
            applicationContext.getString(R.string.noti_action_pause),
            getPendingIntent(ACTION_UPDATE_PAUSE)
        )
        cancelAction = NotificationCompat.Action(
            R.drawable.ic_noti_action_cancel,
            applicationContext.getString(R.string.noti_action_cancel),
            getPendingIntent(ACTION_UPDATE_CANCEL)
        )
        reTryAction = NotificationCompat.Action(
            R.drawable.ic_noti_action_cancel,
            applicationContext.getString(R.string.noti_action_retry),
            getPendingIntent(ACTION_UPDATE_RETRY)
        )
    }

    private fun registerReceiver() {
        log("update service   registerReceiver().", tag = "UpdateService")
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_START))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_FAIL))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_SUCCESS))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_RETRY))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_PROCESS))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_CANCEL))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_PAUSE))
        registerReceiver(updateReceiver, IntentFilter(ACTION_UPDATE_INSTALL))

    }

    private fun unregisterReceiver() {
        log("update service   unregisterReceiver().", tag = "UpdateService")
        unregisterReceiver(updateReceiver)
    }


    fun showNotification() {
        log("update service   showNotification().", tag = "UpdateService")

        notificationCompatBuilder.addAction(pauseAction)
        notificationCompatBuilder.addAction(cancelAction)
        val pIntent = getPendingIntent(ACTION_UPDATE_INSTALL)
        notificationCompatBuilder.setContentIntent(pIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan1 = NotificationChannel(
                NotificationChannelID,
                getString(R.string.noti_channel_default),
                NotificationManager.IMPORTANCE_HIGH
            )
            chan1.enableLights(true)
            chan1.lightColor = Color.GREEN
            chan1.setShowBadge(true)
            chan1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(chan1)
        }
        startForeground(NOTIFICATION_ID, notificationCompatBuilder.build())

    }

    private fun getPendingIntent(type: String): PendingIntent {
        val intent = Intent(type)
        return PendingIntent.getBroadcast(this, 0, intent, 0)
    }

    fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(true)
    }

    private fun updateNotificationProcess(process: Int) {
        log("update service   updateNotificationProcess()-->$process.", tag = "UpdateService")
        if (process < 100) {
            updateNotificationProcessContent(process)
        } else if (process >= 100) {
            updateNotificationProcessContentToDownLoadSuccess()
        }
    }

    private fun updateNotificationProcessContentToDownLoadSuccess() {
        log(
            "update service   updateNotificationProcessContentToDownLoadSuccess().",
            tag = "UpdateService"
        )
        notificationCompatBuilder.apply {
            removeActions()
            setProgress(0, 0, false)
            setContentTitle(applicationContext.getString(R.string.update_finish_title))
            setContentText(applicationContext.getString(R.string.update_finish))
            val pIntent = getPendingIntent(ACTION_UPDATE_INSTALL)
            setContentIntent(pIntent)
        }
        notifyNotification()
    }

    private fun updateNotificationProcessContentToDownLoadFail() {
        notificationCompatBuilder.apply {
            setProgress(0, 0, false)
            setContentTitle(applicationContext.getString(R.string.update_fail_title))
            setContentText(applicationContext.getString(R.string.update_fail))
            val pIntent = getPendingIntent(ACTION_UPDATE_RETRY)
            setContentIntent(pIntent)
        }
        notifyNotification()
    }

    private fun updateNotificationProcessContentToDownLoadRetry() {
        notificationCompatBuilder.apply {
            removeActions()
            setContentTitle(
                applicationContext.getString(
                    R.string.is_updating
                )
            )
        }
        updateNotificationProcessContent(0)
    }

    private fun updateNotificationProcessContent(process: Int) {
        log("update service   updateNotificationProcessContent().", tag = "UpdateService")
        notificationCompatBuilder.apply {
            removeActions()
            addAction(reTryAction)
            addAction(cancelAction)
            setProgress(100, process, false)
            setContentText(
                applicationContext.getString(
                    R.string.update_process,
                    "${process}%"
                )
            )
        }
        notifyNotification()
    }

    private fun notifyNotification() {
        log("update service   notifyNotification().", tag = "UpdateService")
        notificationManager.notify(NOTIFICATION_ID, notificationCompatBuilder.build())
    }
}
