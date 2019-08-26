package com.mml.onceapplication.utils

/**
 * 项目名称：OnceApplication
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 16:28
 */


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import com.baidu.speech.VoiceRecognitionService

/**
 * 语音识别，将语音转换为文字
 *
 */
class MySpeechRecognizer(context: Context) {
    /**
     * MySpeechRecognizer的构造方法
     * @param context 上下文对象
     */
    // 创建识别器
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(
        context,
        ComponentName(context, VoiceRecognitionService::class.java)
    )
    private var callBack: SpeechRecognizerCallBack? = null

    init {

        // 注册监听器
        speechRecognizer.setRecognitionListener(MyRecognitionListener())
    }

    /**
     * 设置回调接口
     * @param callBack 是SpeechRecognizerCallBack对象
     */
    fun setCallBack(callBack: SpeechRecognizerCallBack) {
        this.callBack = callBack
    }

    /**
     * 开始语音识别
     */

    fun Start() {
        val intent = Intent()
        speechRecognizer.startListening(intent)
    }

    /**
     * 开始语音识别
     * @param intent Inetnt对象，所有识别的参数都需要在intent中设置
     */
    fun Start(intent: Intent) {
        speechRecognizer.startListening(intent)
    }

    /**
     * 停止录音，但是识别将继续
     */
    fun Stop() {
        speechRecognizer.stopListening()
    }

    /**
     * 取消本次识别，已有录音也将不再识别
     */
    fun cancel() {
        speechRecognizer.cancel()
    }

    /**
     * 销毁语音识别器，释放资源
     */
    fun Destroy() {
        speechRecognizer.destroy()
    }

    private inner class MyRecognitionListener : RecognitionListener {
        /**
         * 开始说话，当用户开始说话，会回调此方法。
         */
        override fun onBeginningOfSpeech() {

        }

        /**
         * 音量变化，引擎将对每一帧语音回调一次该方法返回音量值。
         */
        override fun onRmsChanged(rmsdB: Float) {
            Log.i(TAG +"onRmsChanged",rmsdB.toString())
        }

        /**
         * 获取原始语音，此方法会被回调多次，buffer是当前帧对应的PCM语音数据，拼接后可得到完整的录音数据。
         */
        override fun onBufferReceived(buffer: ByteArray) {
            Log.i(TAG +"onBufferReceived",buffer.toString())
        }

        /**
         * 说话结束，当用户停止说话后，将会回调此方法。
         */
        override fun onEndOfSpeech() {

        }

        /**
         * 识别出错，识别出错，将会回调此方法，调用该方法之后将不再调用onResults方法。
         * @param error 错误码
         */
        override fun onError(error: Int) {
            Log.e(TAG +"error",error.toString())
        }

        /**
         * 识别最终结果，返回最终识别结果，将会回调此方法。
         * @param results 识别结果
         */
        override fun onResults(results: Bundle) {

            Log.i(TAG,results.toString())
            val text = results.get("results_recognition")!!.toString().replace("]", "").replace("[", "")
            callBack!!.getResult(text)

        }

        /**
         * 识别临时结果，返回临时识别结果，将会回调此方法。
         * @param partialResults 临时结果
         */
        override fun onPartialResults(partialResults: Bundle) {

        }

        /**
         * 识别事件返回，返回识别事件，将会回调此方法。
         * @param eventType 事件类型
         * @param params 参数
         */
        override fun onEvent(eventType: Int, params: Bundle) {

        }

        /**
         * 识别准备就绪，只有当此方法回调之后才能开始说话，否则会影响识别结果。
         */
        override fun onReadyForSpeech(params: Bundle) {


        }
    }

    /**
     * 识别结果回调接口
     * @author Administrator
     */
    interface SpeechRecognizerCallBack {
        /**
         * 返回结果
         * @param result String 结果
         */
        fun getResult(result: String)
    }

    companion object {

        val TAG = MySpeechRecognizer::class.java.simpleName
    }
}