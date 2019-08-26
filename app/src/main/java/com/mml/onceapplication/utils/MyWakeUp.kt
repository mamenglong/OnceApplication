package com.mml.onceapplication.utils

/**
 * 项目名称：OnceApplication
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 17:08
 */
import android.content.Context
import android.util.AndroidRuntimeException
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.baidu.speech.asr.SpeechConstant.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MyWakeUp(val context: Context) {
    //create方法示是一个静态方法，还有一个重载方法EventManagerFactory.create(context, name, version)
    //由于百度文档没有给出每个参数具体含义，我们只能按照官网给的demo写了

    private var mWpEventManager: EventManager? = null
    private var isStart = false

    private var eventListener: EventListener? = null

    init {

        if (isInited) {
            throw RuntimeException("还未调用release()，请勿新建一个新类")
        }
        isInited = true
        mWpEventManager = EventManagerFactory.create(context, "wp")
        //注册监听事件
        eventListener = MyEventListener()
        mWpEventManager?.registerListener(eventListener)
    }

    /**
     * 释放
     */
    fun release() {
        stop()
        mWpEventManager?.unregisterListener(eventListener)
        mWpEventManager = null
        isInited = false
    }

    fun isStart() = isStart
    /**
     * 开启唤醒功能
     */
    fun start() {
        if (!isStart) {
            isStart = true
            val params = HashMap<String, Any>()
            // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
            //  params["kws-file"] = "assets:///WakeUp.bin"
            params[SpeechConstant.WP_WORDS_FILE] = "assets://WakeUp.bin"
            params[com.baidu.speech.asr.SpeechConstant.APP_ID] = "15971505"
            params[com.baidu.speech.asr.SpeechConstant.ACCEPT_AUDIO_VOLUME] = false

            val json = JSONObject(params).toString()
            Log.i(TAG, json)
            mWpEventManager?.send("wp.start", json, null, 0, 0)
            Log.d(TAG, "----->唤醒已经开始工作了")
        } else {
            Log.d(TAG, "----->唤醒已经在工作了")
        }
    }

    /**
     * 关闭唤醒功能
     */
    fun stop() {
        // 具体参数的百度没有具体说明，大体需要以下参数
        // send(String arg1, byte[] arg2, int arg3, int arg4)
        if (isStart) {
            isStart = false
            mWpEventManager?.send("wp.stop", null, null, 0, 0)
            Log.d(TAG, "----->唤醒已经停止")
        } else {
            Log.d(TAG, "----->唤醒已停止")
        }
    }

    /**
     *
     */
    fun setEventListener(eventListener: EventListener) {
        this.eventListener = eventListener
    }

//    fun setEventListener(eventListener: IWakeupListener) {
//        this.eventListener = WakeupEventAdapter(eventListener)
//    }
    private inner class MyEventListener : EventListener {
        override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
            Log.d(TAG, String.format("event: name=%s, params=%s", name, params))
            try {
                when (name) {
                    CALLBACK_EVENT_WAKEUP_STARTED -> {//开启唤醒
                        Log.i(TAG, "开启唤醒")
                    }
                    CALLBACK_EVENT_WAKEUP_SUCCESS -> {
                        // 每次唤醒成功, 将会回调name=wp.data的时间, 被激活的唤醒词在params的word字段
                        val json = JSONObject(params)
                        val errorCode = json.getInt("errorCode")
                        if (errorCode == 0) {
                            //唤醒成功
                            val word = json.getString("word") // 唤醒词
                            when (word) {
                                WakeUpType.WEATHER.value -> {
                                    Log.i(TAG, WakeUpType.WEATHER.value)
                                }
                                WakeUpType.INCREASE_VOLUME.value -> {
                                    Log.i(TAG, WakeUpType.INCREASE_VOLUME.value)
                                }
                                WakeUpType.DECREASE_VOLUME.value -> {
                                    Log.i(TAG, WakeUpType.DECREASE_VOLUME.value)
                                }
                            }
                        } else {
                            //唤醒失败
                            Log.i(TAG, "唤醒失败")
                        }
                    }
                    CALLBACK_EVENT_WAKEUP_STOPED -> {//退出唤醒
                        Log.i(TAG, "退出唤醒")
                    }
                    CALLBACK_EVENT_WAKEUP_ERROR -> {//异常
                        Log.d(TAG, "唤醒异常")
                    }
                }
            } catch (e: JSONException) {
                throw AndroidRuntimeException(e)
            }
        }
    }

    enum class WakeUpType(val value: String) {
        WEATHER("天气预报"),
        INCREASE_VOLUME("增大音量"),
        DECREASE_VOLUME("减小音量")
    }

    companion object {
        var isInited = false
        val TAG = MyWakeUp::class.java.simpleName
    }
}