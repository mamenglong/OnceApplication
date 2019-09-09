package com.mml.onceapplication.dialog

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import androidx.fragment.app.FragmentManager

/**
 * @Author: Menglong Ma
 * @Email: mml2015@126.com
 * @Date: 19-8-29 下午4:19
 * @Description: This is CustomDialogConfig
 * @Package: com.mml.onceapplication.dialog
 * @see [百分比宽高和宽高只有一个生效,如果同时设置则使用宽高,默认为百分比宽高,宽高优先级较高,设置宽高时请使用px像素而不是dp]
 * @Project: OnceApplication
 */
data class SimpleDialogConfig(
    var TAG: String = "SimpleDialogConfig",
    var activity: Activity?=null,
    var message: String="",
    var onCancelClick: () -> Unit = {showDebugToast("onCancelClick")},
    var onConfirmClick: () -> Unit = {showDebugToast("onConfirmClick")},
    var onDismissCallback:()->Unit={showDebugToast("onDismissCallback")},
    var cancelable: Boolean = true,
    var title: String = "标题",
    var cancelText: String = "取消",
    var confirmText: String = "确认"
)