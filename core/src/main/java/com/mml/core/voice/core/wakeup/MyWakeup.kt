package com.mml.core.voice.core.wakeup

import android.content.Context
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.mml.core.voice.core.util.MyLogger
import org.json.JSONObject

/**
 * Created by fujiayi on 2017/6/20.
 */

class MyWakeup(context: Context, private var eventListener: EventListener?) {

    private var wp: EventManager? = null

    init {
        if (isInited) {
            MyLogger.error(TAG, "还未调用release()，请勿新建一个新类")
            throw RuntimeException("还未调用release()，请勿新建一个新类")
        }
        isInited = true
        wp = EventManagerFactory.create(context, "wp")
        wp!!.registerListener(eventListener)
    }

    constructor(context: Context, eventListener: IWakeupListener) : this(context, WakeupEventAdapter(eventListener))

    fun start(params: Map<String, Any>) {
        val json = JSONObject(params).toString()
        MyLogger.info("$TAG.Debug", "wakeup params(反馈请带上此行日志):$json")
        wp!!.send(SpeechConstant.WAKEUP_START, json, null, 0, 0)
    }

    fun stop() {
        MyLogger.info(TAG, "唤醒结束")
        wp!!.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0)
    }

    fun setEventListener(eventListener: EventListener) {
        this.eventListener = eventListener
    }

    fun setEventListener(eventListener: IWakeupListener) {
        this.eventListener = WakeupEventAdapter(eventListener)
    }

    fun release() {
        stop()
        wp!!.unregisterListener(eventListener)
        wp = null
        isInited = false
    }

    companion object {


        private var isInited = false

        private val TAG = "MyWakeup"
    }
}
