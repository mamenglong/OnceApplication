package com.mml.core.voice.core.recog.listener

/**
 * Created by fujiayi on 2017/6/14.
 */

import android.util.Log
import com.mml.core.voice.core.recog.IStatus
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_FINISHED
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_LONG_SPEECH_FINISHED
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_NONE
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_READY
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_RECOGNITION
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_SPEAKING
import com.mml.core.voice.core.recog.RecogResult

/**
 * 根据回调，判断asr引擎的状态
 *
 * 通常状态变化如下：
 *
 * STATUS_NONE 初始状态
 * STATUS_READY 引擎准备完毕
 * STATUS_SPEAKING 用户开始说话到用户说话完毕前
 * STATUS_RECOGNITION 用户说话完毕后，识别结束前
 * STATUS_FINISHED 获得最终识别结果
 */
open class StatusRecogListener : IRecogListener, IStatus {

    /**
     * 识别的引擎当前的状态
     */
    protected var status = STATUS_NONE

    override fun onAsrReady() {
        status = STATUS_READY
    }

    override fun onAsrBegin() {
        status = STATUS_SPEAKING
    }

    override fun onAsrEnd() {
        status = STATUS_RECOGNITION
    }

    override fun onAsrPartialResult(results: Array<String>?, recogResult: RecogResult) {

    }

    override fun onAsrFinalResult(results: Array<String>?, recogResult: RecogResult) {
        status = STATUS_FINISHED
    }

    override fun onAsrFinish(recogResult: RecogResult) {
        status = STATUS_FINISHED
    }


    override fun onAsrFinishError(
        errorCode: Int, subErrorCode: Int, descMessage: String,
        recogResult: RecogResult
    ) {
        status = STATUS_FINISHED
    }

    override fun onAsrLongFinish() {
        status = STATUS_LONG_SPEECH_FINISHED
    }

    override fun onAsrVolume(volumePercent: Int, volume: Int) {
        Log.i(TAG, "音量百分比$volumePercent ; 音量$volume")
    }

    override fun onAsrAudio(data: ByteArray, offset: Int, length: Int) {
        var data = data
        if (offset != 0 || data.size != length) {
            val actualData = ByteArray(length)
            System.arraycopy(data, 0, actualData, 0, length)
            data = actualData
        }

        Log.i(TAG, "音频数据回调, length:" + data.size)
    }

    override fun onAsrExit() {
        status = STATUS_NONE
    }

    override fun onAsrOnlineNluResult(nluResult: String) {
        status = STATUS_FINISHED
    }

    override fun onOfflineLoaded() {

    }

    override fun onOfflineUnLoaded() {

    }

    companion object {

        private val TAG = "StatusRecogListener"
    }


}
