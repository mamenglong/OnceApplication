package com.mml.core.voice.core.inputstream

import android.annotation.SuppressLint
import android.app.Activity
import com.mml.core.voice.core.util.MyLogger

import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Created by fujiayi on 2017/6/20.
 */

@SuppressLint("StaticFieldLeak")
object InFileStream {

    private var context: Activity? = null

    private val TAG = "InFileStream"

    private var filename: String? = null

    private var `is`: InputStream? = null

    fun setContext(context: Activity) {
        InFileStream.context = context
    }

    fun reset() {
        filename = null
        `is` = null
    }

    fun setFileName(filename: String) {
        InFileStream.filename = filename
    }

    fun setInputStream(is2: InputStream) {
        `is` = is2
    }

    fun create16kStream(): InputStream? {
        if (`is` != null) {
            return FileAudioInputStream(`is`!!)
        } else if (filename != null) {
            try {
                return FileAudioInputStream(filename!!)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        } else {
            return FileAudioInputStream(createFileStream())
        }
        return null
    }

    private fun createFileStream(): InputStream {
        try {
            val `is` = context!!.assets.open("outfile.pcm")
            MyLogger.info(TAG, "create input stream ok " + `is`.available())
            return `is`
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }
}