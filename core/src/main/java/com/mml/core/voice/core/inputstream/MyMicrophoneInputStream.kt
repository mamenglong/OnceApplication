package com.mml.core.voice.core.inputstream

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import org.jetbrains.annotations.NotNull

import java.io.IOException
import java.io.InputStream

/**
 * Created by fujiayi on 2017/11/27.
 */

class MyMicrophoneInputStream : InputStream() {

    private var isStarted = false

    init {
        if (audioRecord == null) {
            val bufferSize = AudioRecord.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
            ) * 16
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize
            )
        }
    }

    fun start() {
        Log.i(TAG, " MyMicrophoneInputStream start recoding!")
        try {
            if (audioRecord == null || audioRecord!!.state != AudioRecord.STATE_INITIALIZED) {
                throw IllegalStateException(
                    "startRecording() called on an uninitialized AudioRecord." + (audioRecord == null)
                )
            }
            audioRecord!!.startRecording()
        } catch (e: Exception) {
            Log.e(TAG, e.javaClass.getSimpleName(), e)
        }

        Log.i(TAG, " MyMicrophoneInputStream start recoding finished")
    }

    @Throws(IOException::class)
    override fun read(): Int {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun read(@NotNull b: ByteArray, off: Int, len: Int): Int {
        if (!isStarted) {
            start() // 建议在CALLBACK_EVENT_ASR_READY事件中调用。
            isStarted = true
        }
        try {
// Log.i(TAG, " MyMicrophoneInputStream read count:" + count);
            return audioRecord!!.read(b, off, len)
        } catch (e: Exception) {
            Log.e(TAG, e.javaClass.getSimpleName(), e)
            throw e
        }

    }

    @Throws(IOException::class)
    override fun close() {
        Log.i(TAG, " MyMicrophoneInputStream close")
        if (audioRecord != null) {
            audioRecord!!.stop()
            // audioRecord.release(); 程序结束别忘记自行释放
            isStarted = false
        }
    }

    companion object {
        private var audioRecord: AudioRecord? = null

        private var `is`: MyMicrophoneInputStream? = null

        private val TAG = "MyMicrophoneInputStream"

        val instance: MyMicrophoneInputStream
            get() {
                if (`is` == null) {
                    synchronized(MyMicrophoneInputStream::class.java) {
                        if (`is` == null) {
                            `is` = MyMicrophoneInputStream()
                        }
                    }
                }
                return this!!.`is`!!
            }
    }
}
