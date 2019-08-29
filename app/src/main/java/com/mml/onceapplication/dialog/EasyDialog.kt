package com.mml.onceapplication.dialog

import android.content.DialogInterface
import android.view.View


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-29 下午12:00
 * Description:[DialogFragment的基类1.系统默认onCreateDialog方法返回一个Dialog对象,对其不做处理2.主要操作onCreateView方法 因为DialogFragment继承自Fragment,所以可以在onCreteView()方法返回xml布局,该布局在onActivityCreated()方法中,设置给系统之前创建的Dialog对象]
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */


class EasyDialog : BaseDialog() {
    private var onDismissCallback: (() -> Unit)? = null
    private var convert: ((view: View) -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback?.invoke()
    }

    override fun bindView(view: View) {
        convert?.invoke(view)
    }

    fun setLayoutRes(layoutId: Int): EasyDialog {
        dialogConfig.layoutResId = layoutId
        return this
    }

    fun setCustomView(view: View): EasyDialog {
        dialogConfig.customDialogView = view
        return this
    }

    fun setOnDismissCallback(onDismissCallback: () -> Unit): EasyDialog {
        this.onDismissCallback = onDismissCallback
        return this
    }

    fun convert(convert: (view: View) -> Unit): EasyDialog {
        this.convert = convert
        return this
    }
    fun setDimAmount(value:Float): EasyDialog {
        dialogConfig.dimAmount = value
        return this
    }
    fun setGravity(value: Int): EasyDialog {
        dialogConfig.gravity=value
        return this
    }
    fun setDialogAnimationRes(value: Int): EasyDialog {
        dialogConfig.dialogAnimationRes=value
        return this
    }
    fun setWidth(value: Int): EasyDialog {
        dialogConfig.width=value
        return this
    }
    fun setHeight(value: Int): EasyDialog {
        dialogConfig.height=value
        return this
    }
    fun setTag(value: String): EasyDialog {
        dialogConfig.TAG=value
        return this
    }
    fun setCancelableOutside(value: Boolean): EasyDialog {
        dialogConfig.isCancelableOutside=value
        return this
    }
}