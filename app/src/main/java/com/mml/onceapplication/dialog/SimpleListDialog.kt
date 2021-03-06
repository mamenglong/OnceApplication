package com.mml.onceapplication.dialog

import android.app.Activity
import android.app.AlertDialog
import android.widget.ArrayAdapter
import java.lang.NullPointerException
import android.widget.EditText
import com.mml.onceapplication.log


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 上午10:08
 * Description: This is SimpleDialog
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
class SimpleListDialog:ISimpleListDialogConfig<SimpleListDialog>{

    private val simpleDialogConfig=SimpleListDialogConfig()
    private var editText: EditText?=null
    fun init(activity: Activity):SimpleListDialog{
        simpleDialogConfig.activity=activity
        editText=EditText(activity)
        return this
    }
    /**
     * @Author: Menglong Ma
     * @Email: mml2015@126.com
     * @Date: 19-9-9 下午6:05
     * @Description: 所以参数设置以后调用
     */
    fun show() {
        val adapter= ArrayAdapter<Any>(simpleDialogConfig.activity!!, com.mml.onceapplication.R.layout.support_simple_spinner_dropdown_item,simpleDialogConfig.items!!)
        with(simpleDialogConfig) {
            activity?:throw NullPointerException("parameter activity is null ,please call init(activity: Activity) before show()")
           val dialog= AlertDialog.Builder(activity)
                .setTitle(title)
//               .setView(editText)
                .setAdapter(adapter) { _, pos->
                 log(tag="SimpleListDialog",msg ="${items!![pos]}")

                }
           /*    .setItems(*(items!!)) { _, pos->
                   log(tag="SimpleEditTextDialog setItems",msg ="${items!![pos]}")

               }*/
             /*  .setSingleChoiceItems(data1,-1){ _, pos->
                   log(tag="SimpleEditTextDialog  setSingleChoiceItems",msg ="${data1[pos]}")
               }*/
            /*   .setMultiChoiceItems(data1,booleanValue) { _, pos, choose->
                   booleanValue[pos]=choose
                   log(tag="SimpleEditTextDialog  setMultiChoiceItems",msg ="$booleanValue")
               }*/
               .setPositiveButton(confirmText) { _, _ ->
                    onConfirmClickCallback.invoke(editText?.text.toString())
                }
                .setNegativeButton(cancelText) { _, _ ->
                    onCancelClickCallback.invoke()
                }
                .setCancelable(cancelable)
                .create()
            dialog.setOnDismissListener { onDismissCallback.invoke() }
            dialog.show()

            val m = activity!!.windowManager
            val d = m.defaultDisplay  //为获取屏幕宽、高
            val p = dialog.window!!.attributes  //获取对话框当前的参数值
            if (p.height>(d.height * 0.5).toInt()) {
                p.height = (d.height * 0.5).toInt()   //高度设置为屏幕的0.3
                p.width = (d.width * 0.8).toInt()    //宽度设置为屏幕的0.5
                dialog.window!!.attributes = p     //设置生效
            }
        }
    }
    override fun isCancelable(isCan: Boolean): SimpleListDialog {
        simpleDialogConfig.cancelable=isCan
        return this
    }

    override fun setTAG(tag: String): SimpleListDialog {
        simpleDialogConfig.TAG=tag
        return this
    }

    override fun setActivity(activity: Activity): SimpleListDialog {
        simpleDialogConfig.activity=activity
        return this
    }

    override fun setMessage(msg: String): SimpleListDialog {
        simpleDialogConfig.message=msg
        return this
    }

    override fun setOnCancelClickCallback(cancelCallback: () -> Unit): SimpleListDialog {
        simpleDialogConfig.onCancelClickCallback=cancelCallback
        return this
    }
    override fun setOnConfirmClickCallback(confirmCallback: (value:String) -> Unit): SimpleListDialog {
        simpleDialogConfig.onConfirmClickCallback=confirmCallback
        return this
    }

    override fun setOnDismissCallback(dismissCallback: () -> Unit): SimpleListDialog {
        simpleDialogConfig.onDismissCallback=dismissCallback
        return this
    }

    override fun setTitle(title: String): SimpleListDialog {
        simpleDialogConfig.title=title
        return this
    }

    override fun setCancelText(value: String): SimpleListDialog {
        simpleDialogConfig.cancelText=value
        return this
    }

    override fun setConfirmText(value: String): SimpleListDialog {
        simpleDialogConfig.confirmText=value
        return this
    }

    override fun setItems(items: ArrayList<Any>): SimpleListDialog {
         simpleDialogConfig.items=items
        return this
    }
}