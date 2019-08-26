package com.mml.core.voice.core.wakeup

import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.mml.core.voice.core.util.MyLogger

/**
 *
 * @author
 * @date 2017/6/20
 */

class WakeupEventAdapter(private val listener: IWakeupListener) : EventListener {
    // 基于DEMO唤醒3.1 开始回调事件
    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
        // android studio日志Monitor 中搜索 WakeupEventAdapter即可看见下面一行的日志
        MyLogger.info(TAG, "wakeup name:$name; params:$params")
        if (SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS == name) { // 识别唤醒词成功
            val result = WakeUpResult.parseJson(name, params!!)
            val errorCode = result.errorCode
            if (result.hasError()) { // error不为0依旧有可能是异常情况
                listener.onError(errorCode, "", result)
            } else {
                val word = result.word
                listener.onSuccess(word!!, result)

            }
        } else if (SpeechConstant.CALLBACK_EVENT_WAKEUP_ERROR == name) { // 识别唤醒词报错
            val result = WakeUpResult.parseJson(name, params!!)
            val errorCode = result.errorCode
            if (result.hasError()) {
                listener.onError(errorCode, "", result)
            }
        } else if (SpeechConstant.CALLBACK_EVENT_WAKEUP_STOPED == name) { // 关闭唤醒词
            listener.onStop()
        } else if (SpeechConstant.CALLBACK_EVENT_WAKEUP_AUDIO == name) { // 音频回调
            listener.onASrAudio(data!!, offset, length)
        }
    }

    companion object {

        private val TAG = "WakeupEventAdapter"
    }
}
