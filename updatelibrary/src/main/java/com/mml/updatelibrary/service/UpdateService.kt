package com.mml.updatelibrary.service

import android.app.*
import android.content.*
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.mml.updatelibrary.R
import com.mml.updatelibrary.Utils
import com.mml.updatelibrary.log

class UpdateService : Service() {
    companion object {

        const val UPDATE_START_ACTION = "com.mml.updatelibrary.service.update_start"
        const val UPDATE_FAIL_ACTION = "com.mml.updatelibrary.service.update_fail"
        const val UPDATE_RETRY_ACTION = "com.mml.updatelibrary.service.update_retry"
        const val UPDATE_PROCESS_ACTION = "com.mml.updatelibrary.service.update_process"
        const val UPDATE_CANCEL_ACTION = "com.mml.updatelibrary.service.update_cancel"
        const val UPDATE_PAUSE_ACTION = "com.mml.updatelibrary.service.update_pause"
        const val UPDATE_INSTALL_ACTION = "com.mml.updatelibrary.service.update_install"
        const val NotificationChannelID = "default"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var notificationCompatBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    private var process = 10
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    UPDATE_START_ACTION -> {
                        log(UPDATE_START_ACTION,tag ="UpdateService" )

                        showNotification(false)
                    }
                    UPDATE_PROCESS_ACTION -> {
                        log(UPDATE_PROCESS_ACTION,tag ="UpdateService" )

                        process += 10
                        updateNotificationProcess(process)
                    }
                    UPDATE_CANCEL_ACTION -> {
                        log(UPDATE_CANCEL_ACTION,tag ="UpdateService" )

                        cancelNotification()
                    }
                    UPDATE_FAIL_ACTION -> {
                        log(UPDATE_FAIL_ACTION,tag ="UpdateService" )

                    }
                    UPDATE_RETRY_ACTION -> {
                        log(UPDATE_RETRY_ACTION,tag ="UpdateService" )

                    }
                    UPDATE_INSTALL_ACTION -> {
                        log(UPDATE_INSTALL_ACTION,tag ="UpdateService" )

//                        Utils.installApk()
                    }
                    UPDATE_PAUSE_ACTION -> {
                        log(UPDATE_PAUSE_ACTION,tag ="UpdateService" )
                    }
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
        sendBroadcast(Intent(UPDATE_START_ACTION))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("update service   onStartCommand.", tag = "UpdateService")
        return super.onStartCommand(intent, flags, startId)

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
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setSubText("6666")
                .setOngoing(true)    //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知

    }

    private fun registerReceiver() {
        log("update service   registerReceiver().", tag = "UpdateService")
        registerReceiver(updateReceiver, IntentFilter(UPDATE_START_ACTION))
        registerReceiver(updateReceiver, IntentFilter(UPDATE_FAIL_ACTION))

        registerReceiver(updateReceiver, IntentFilter(UPDATE_RETRY_ACTION))

        registerReceiver(updateReceiver, IntentFilter(UPDATE_PROCESS_ACTION))

        registerReceiver(updateReceiver, IntentFilter(UPDATE_CANCEL_ACTION))
        registerReceiver(updateReceiver, IntentFilter(UPDATE_PAUSE_ACTION))

        registerReceiver(updateReceiver, IntentFilter(UPDATE_INSTALL_ACTION))

    }

    private fun unregisterReceiver() {
        log("update service   unregisterReceiver().", tag = "UpdateService")
        unregisterReceiver(updateReceiver)
    }


    fun showNotification(isPaused: Boolean) {
        log("update service   showNotification().", tag = "UpdateService")
        val pIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(UPDATE_INSTALL_ACTION),
            0
        )

        /*       if (isPaused) {
                   builder.addAction(
                       R.drawable.ic_noti_action_resume, context.getString(R.string.noti_action_resume),
                       getPendingIntent(context, ACTION_RESUME)
                   )
               } else {
                   builder.addAction(
                       R.drawable.ic_noti_action_pause,
                       context.getString(R.string.noti_action_pause),
                       getPendingIntent(context, ACTION_PAUSE)
                   )
               }*/
        notificationCompatBuilder.addAction(
            R.drawable.ic_noti_action_pause,
            applicationContext.getString(R.string.noti_action_pause),
            getPendingIntent(UPDATE_PAUSE_ACTION)
        )
        notificationCompatBuilder.addAction(
            R.drawable.ic_noti_action_cancel,
            applicationContext.getString(R.string.noti_action_cancel),
            getPendingIntent(UPDATE_CANCEL_ACTION)
        )
            .setContentIntent(pIntent)


        startForeground(NOTIFICATION_ID, notificationCompatBuilder.build())

    }

    private fun getPendingIntent(type: String): PendingIntent {
        val intent = Intent(type)
        return PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
    }

    fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun updateNotificationProcess(process: Int) {
        log("update service   updateNotificationProcess().", tag = "UpdateService")
        if (process <= 100) {
            notificationCompatBuilder.setProgress(100, process, false)
            updateNotificationContent(
                applicationContext.getString(
                    R.string.update_process,
                    "${process}%"
                )
            )
            notifyNotification()
        }

    }

    private fun updateNotificationContent(content: String) {
        notificationCompatBuilder.setContentText(content)
        notifyNotification()
    }

    private fun notifyNotification() {
        notificationManager.notify(NOTIFICATION_ID, notificationCompatBuilder.build())
    }
}
