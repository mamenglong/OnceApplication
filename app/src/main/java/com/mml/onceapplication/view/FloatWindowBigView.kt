package com.mml.onceapplication.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.mml.onceapplication.service.FloatWindowService
import com.mml.onceapplication.R

import com.mml.onceapplication.utils.MyWindowManager


/**
 * 项目名称：kotlin1
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 10:02
 */
class FloatWindowBigView(context: Context) : LinearLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_float_main_big, this)
        val view = this.findViewById<LinearLayout>(R.id.big_window_layout)
        viewWidth = view.layoutParams.width
        viewHeight = view.layoutParams.height
        val close = findViewById<Button>(R.id.close)
        val back = findViewById<Button>(R.id.back)
        close.setOnClickListener {
            // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
            MyWindowManager.removeBigWindow(context)
            MyWindowManager.removeSmallWindow(context)
            val intent = Intent(getContext(), FloatWindowService::class.java)
            context.stopService(intent)
        }
        back.setOnClickListener {
            // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
            MyWindowManager.removeBigWindow(context)
            MyWindowManager.createSmallWindow(context)
        }
    }

    companion object {

        /**
         * 记录大悬浮窗的宽度
         */
        var viewWidth: Int = 0

        /**
         * 记录大悬浮窗的高度
         */
        var viewHeight: Int = 0
    }
}