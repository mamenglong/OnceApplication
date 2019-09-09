package com.mml.onceapplication.dialog

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.widget.DialogTitle
import com.mml.onceapplication.BuildConfig
import com.mml.onceapplication.OnceApplication

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 下午5:03
 * Description: This is IDialogCancelCallBack
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
fun showDebugToast(msg:String)= run {
    if (BuildConfig.DEBUG) {
        Toast.makeText(OnceApplication.instances, msg, Toast.LENGTH_SHORT).show()
    }
}
interface IDialogCancelCallBack<T> {
    fun onCancel(onCancelCallback: () -> Unit):T
}
interface IDialogDismissCallBack<T>  {
    fun onDismiss(onDismissCallback: () -> Unit):T
}
interface IDialogConfirmCallBack<T>  {
    fun onConfirm(onConfirmCallback: () -> Unit):T
}

interface IDialogCallBack<T>:IDialogCancelCallBack<T>,IDialogDismissCallBack<T>,IDialogConfirmCallBack<T>



interface ISimpleDialogConfig<T>{
    fun isCancelable(isCan:Boolean):T
    fun setTAG(tag:String):T
    fun setActivity(activity: Activity):T
    fun setMessage(msg: String):T
    fun setOnCancelClick(onCancelCallback: () -> Unit):T
    fun setOnConfirmClick(onConfirmCallback: () -> Unit):T
    fun setOnDismissCallback(onDismissCallback: () -> Unit):T
    fun setTitle(title: String):T
    fun setCancelText(value:String):T
    fun setConfirmText(value:String):T

}