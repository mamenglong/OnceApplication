package com.mml.onceapplication.service

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.mml.onceapplication.utils.MyWindowManager
import java.util.*


class FloatWindowService : Service() {

    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private val handler = Handler()

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private var timer: Timer? = null

    /**
     * 判断当前界面是否是桌面
     */
    private val isHome: Boolean
        @SuppressLint("NewApi")
        get() {
            val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val rti = mActivityManager.getRunningTasks(1)
            return homes.contains(rti[0].topActivity!!.packageName)
        }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private val homes: List<String>
        get() {
            val names = ArrayList<String>()
            val packageManager = this.packageManager
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            val resolveInfo = packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (ri in resolveInfo) {
                names.add(ri.activityInfo.packageName)
            }
            return names
        }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = Timer()
            timer!!.scheduleAtFixedRate(RefreshTask(), 0, 500)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Service被终止的同时也停止定时器继续运行
        timer!!.cancel()
        timer = null
    }

    internal inner class RefreshTask : TimerTask() {

        override fun run() {
            Log.i("RefreshTask ishome","$isHome")
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            if (isHome && !MyWindowManager.isWindowShowing) {
                handler.post {
                    MyWindowManager.createSmallWindow(applicationContext)
                }
            } else if (!isHome && MyWindowManager.isWindowShowing) {
                // 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗。
                handler.post {
                    MyWindowManager.removeSmallWindow(applicationContext)
                    MyWindowManager.removeBigWindow(applicationContext)
                }
            } else if (isHome && MyWindowManager.isWindowShowing) {
                handler.post {
                    MyWindowManager.updateUsedPercent(applicationContext)
                }
            }// 当前界面是桌面，且有悬浮窗显示，则更新内存数据。

        }

    }
}
