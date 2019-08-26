package com.mml.core.voice.core.util

import android.os.Handler
import android.os.Message
import android.util.Log


/**
 * 记录日志的时候， 顺带往handler记录一份
 */

object MyLogger {
    private val TAG = "MyLogger"

    private val INFO = "INFO"

    private val ERROR = "ERROR"

    private val ENABLE = true

    private var handler: Handler? = null

    fun info(message: String) {
        info(TAG, message)
    }

    fun info(tag: String, message: String) {
        log(INFO, tag, message)
    }

    fun error(message: String) {
        error(TAG, message)
    }

    fun error(tag: String, message: String) {
        log(ERROR, tag, message)
    }

    fun setHandler(handler: Handler) {
        MyLogger.handler = handler
    }

    private fun log(level: String, tag: String, message: String) {
        if (!ENABLE) {
            return
        }
        if (level == INFO) {
            Log.i(tag, message)

        } else if (level == ERROR) {
            Log.e(tag, message)
        }
        if (handler != null) {
            val msg = Message.obtain()
            msg.obj = "[$level]$message\n"
            handler!!.sendMessage(msg)
        }
    }
}
