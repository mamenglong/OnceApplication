package com.mml.core.voice.core.inputstream

import android.util.Log

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Created by fujiayi on 2017/11/27.
 *
 *
 * 解决大文件的输入问题。
 * 文件大时不能通过Infile参数一下子输入。
 */

class FileAudioInputStream : InputStream {

    private var `in`: InputStream? = null

    private var nextSleepTime: Long = -1

    private var totalSleepMs: Long = 0

    @Throws(FileNotFoundException::class)
    constructor(file: String) {
        `in` = FileInputStream(file)
    }

    constructor(`in`: InputStream) {
        this.`in` = `in`
    }

    @Throws(IOException::class)
    override fun read(): Int {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun read(buffer: ByteArray, byteOffset: Int, byteCount: Int): Int {
        val bytePerMs = 16000 * 2 / 1000
        var count = bytePerMs * 20 // 10ms 音频数据
        if (byteCount < count) {
            count = byteCount
        }
        if (nextSleepTime > 0) {
            try {
                val sleepMs = nextSleepTime - System.currentTimeMillis()
                if (sleepMs > 0) {
                    Log.i(TAG, "will sleep $sleepMs")
                    Thread.sleep(sleepMs)
                    totalSleepMs += sleepMs
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        val r = `in`!!.read(buffer, byteOffset, count)

        nextSleepTime = System.currentTimeMillis() + r / bytePerMs
        return r
    }

    @Throws(IOException::class)
    override fun close() {
        super.close()
        Log.i(TAG, "time sleeped $totalSleepMs")
        if (null != `in`) {
            `in`!!.close()
        }
    }

    companion object {

        private val TAG = "FileAudioInputStream"
    }
}
