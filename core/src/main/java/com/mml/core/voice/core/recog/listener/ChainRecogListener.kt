package com.mml.core.voice.core.recog.listener


import com.mml.core.voice.core.recog.RecogResult
import java.util.*

/**
 * Created by fujiayi on 2017/10/18.
 */

class ChainRecogListener : IRecogListener {

    private val listeners: ArrayList<IRecogListener>

    init {
        listeners = ArrayList()
    }

    fun addListener(listener: IRecogListener) {
        listeners.add(listener)
    }

    /**
     * ASR_START 输入事件调用后，引擎准备完毕
     */
    override fun onAsrReady() {
        for (listener in listeners) {
            listener.onAsrReady()
        }
    }

    /**
     * onAsrReady后检查到用户开始说话
     */
    override fun onAsrBegin() {
        for (listener in listeners) {
            listener.onAsrBegin()
        }
    }

    /**
     * 检查到用户开始说话停止，或者ASR_STOP 输入事件调用后，
     */
    override fun onAsrEnd() {
        for (listener in listeners) {
            listener.onAsrEnd()
        }
    }

    /**
     * onAsrBegin 后 随着用户的说话，返回的临时结果
     *
     * @param results     可能返回多个结果，请取第一个结果
     * @param recogResult 完整的结果
     */
    override fun onAsrPartialResult(results: Array<String>?, recogResult: RecogResult) {
        for (listener in listeners) {
            listener.onAsrPartialResult(results, recogResult)
        }
    }

    /**
     * 最终的识别结果
     *
     * @param results     可能返回多个结果，请取第一个结果
     * @param recogResult 完整的结果
     */
    override fun onAsrFinalResult(results: Array<String>?, recogResult: RecogResult) {
        for (listener in listeners) {
            listener.onAsrFinalResult(results, recogResult)
        }
    }

    override fun onAsrFinish(recogResult: RecogResult) {
        for (listener in listeners) {
            listener.onAsrFinish(recogResult)
        }
    }

    override fun onAsrFinishError(
        errorCode: Int, subErrorCode: Int, descMessage: String,
        recogResult: RecogResult
    ) {
        for (listener in listeners) {
            listener.onAsrFinishError(errorCode, subErrorCode, descMessage, recogResult)
        }
    }

    /**
     * 长语音识别结束
     */
    override fun onAsrLongFinish() {
        for (listener in listeners) {
            listener.onAsrLongFinish()
        }
    }

    override fun onAsrVolume(volumePercent: Int, volume: Int) {
        for (listener in listeners) {
            listener.onAsrVolume(volumePercent, volume)
        }
    }

    override fun onAsrAudio(data: ByteArray, offset: Int, length: Int) {
        for (listener in listeners) {
            listener.onAsrAudio(data, offset, length)
        }
    }

    override fun onAsrExit() {
        for (listener in listeners) {
            listener.onAsrExit()
        }
    }

    override fun onAsrOnlineNluResult(nluResult: String) {
        for (listener in listeners) {
            listener.onAsrOnlineNluResult(nluResult)
        }
    }

    override fun onOfflineLoaded() {
        for (listener in listeners) {
            listener.onOfflineLoaded()
        }
    }

    override fun onOfflineUnLoaded() {
        for (listener in listeners) {
            listener.onOfflineUnLoaded()
        }
    }
}
