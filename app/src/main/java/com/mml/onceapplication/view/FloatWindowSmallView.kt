package com.mml.onceapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.mml.onceapplication.R
import com.mml.onceapplication.utils.MyWindowManager


/**
 * 项目名称：kotlin1
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 9:53
 */
class FloatWindowSmallView(context: Context) : LinearLayout(context) {

    /**
     * 用于更新小悬浮窗的位置
     */
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    /**
     * 小悬浮窗的参数
     */
    private var mParams: WindowManager.LayoutParams? = null

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private var xInScreen: Float = 0.toFloat()

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private var yInScreen: Float = 0.toFloat()

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private var xDownInScreen: Float = 0.toFloat()

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private var yDownInScreen: Float = 0.toFloat()

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private var xInView: Float = 0.toFloat()

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private var yInView: Float = 0.toFloat()

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.activity_float_main_small, this)
        val view = inflate.findViewById<LinearLayout>(R.id.small_window_layout)
        viewWidth = view.layoutParams.width
        viewHeight = view.layoutParams.height
        val percentView = findViewById<TextView>(R.id.percent)
        percentView.setText(MyWindowManager.getUsedPercentValue(context))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.x
                yInView = event.y
                xDownInScreen = event.rawX
                yDownInScreen = event.rawY - getStatusBarHeight()
                xInScreen = event.rawX
                yInScreen = event.rawY - getStatusBarHeight()
            }
            MotionEvent.ACTION_MOVE -> {
                xInScreen = event.rawX
                yInScreen = event.rawY - getStatusBarHeight()
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition()
            }
            MotionEvent.ACTION_UP ->
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    openBigWindow()
                }
            else -> {
            }
        }
        return true
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params
     * 小悬浮窗的参数
     */
    fun setParams(params: WindowManager.LayoutParams) {
        mParams = params
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private fun updateViewPosition() {
        mParams!!.x = (xInScreen - xInView).toInt()
        mParams!!.y = (yInScreen - yInView).toInt()
        windowManager.updateViewLayout(this, mParams)
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private fun openBigWindow() {
        MyWindowManager.createBigWindow(context)
        MyWindowManager.removeSmallWindow(context)
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private fun getStatusBarHeight(): Int {
        if (statusBarHeight == 0) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val o = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = field.get(o) as Int
                statusBarHeight = resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return statusBarHeight
    }

    companion object {

        /**
         * 记录小悬浮窗的宽度
         */
        var viewWidth: Int = 0

        /**
         * 记录小悬浮窗的高度
         */
        var viewHeight: Int = 0

        /**
         * 记录系统状态栏的高度
         */
        private var statusBarHeight: Int = 0
    }
}