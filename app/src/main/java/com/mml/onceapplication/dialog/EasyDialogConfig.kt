package com.mml.onceapplication.dialog

import android.view.Gravity
import android.view.View
import android.view.WindowManager

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-29 下午4:19
 * Description: This is EasyDialogConfig
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
data class EasyDialogConfig(
    /**
     * 是否允许点击窗口外面消失
     */
    var isCancelableOutside: Boolean = true,
    var TAG: String = "EasyDialogConfig",
    val DEFAULT_DIMAMOUNT: Float = 0.2f,
    /**
     * 默认透明度为0.2
     */
    var dimAmount: Float = DEFAULT_DIMAMOUNT,

    /**
     * 获取弹窗显示动画
     */
    var dialogAnimationRes: Int = 0,
    /**
     * 默认弹窗位置为中心
     */
    var gravity: Int = Gravity.CENTER,
    /**
     * 默认宽高为包裹内容
     */
    var width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    /**
     * 默认宽高为包裹内容
     */
    var height: Int = WindowManager.LayoutParams.WRAP_CONTENT,

    var layoutResId: Int = 0,
    var customDialogView: View? = null
)