package com.mml.core.voice.core.wakeup

import android.os.Handler
import android.util.Log
import com.mml.core.voice.core.recog.IStatus

/**
 * Created by fujiayi on 2017/9/21.
 */

class RecogWakeupListener(private val handler: Handler) : SimpleWakeupListener(), IStatus {

    override fun onSuccess(word: String, result: WakeUpResult) {
        super.onSuccess(word, result)
        handler.sendMessage(handler.obtainMessage(IStatus.STATUS_WAKEUP_SUCCESS))
    }
    override fun onError(errorCode: Int, errorMessge: String, result: WakeUpResult) {
        super.onError(errorCode, errorMessge, result)
        Log.i("error","errorCode:$errorCode  errmsg:$errorMessge")
        handler.sendMessage(handler.obtainMessage(10001).apply { obj=result.toString() })
    }

    companion object {

        private val TAG = "RecogWakeupListener"
    }
}
