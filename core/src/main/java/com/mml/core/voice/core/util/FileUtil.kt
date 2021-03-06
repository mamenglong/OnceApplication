package com.mml.core.voice.core.util

import android.content.res.AssetManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by fujiayi on 2017/5/19.
 */

object FileUtil {

    fun makeDir(dirPath: String): Boolean {
        val file = File(dirPath)
        return if (!file.exists()) {
            file.mkdirs()
        } else {
            true
        }
    }

    fun getContentFromAssetsFile(assets: AssetManager, source: String): String {
        var `is`: InputStream? = null
        val fos: FileOutputStream? = null
        var result = ""
        try {
            `is` = assets.open(source)
            val lenght = `is`!!.available()
            val buffer = ByteArray(lenght)
            `is`.read(buffer)
            result = String(buffer, java.nio.charset.StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }

    @Throws(IOException::class)
    fun copyFromAssets(
        assets: AssetManager, source: String, dest: String,
        isCover: Boolean
    ): Boolean {
        val file = File(dest)
        var isCopyed = false
        if (isCover || !isCover && !file.exists()) {
            var `is`: InputStream? = null
            var fos: FileOutputStream? = null
            try {
                `is` = assets.open(source)
                fos = FileOutputStream(dest)
                val buffer = ByteArray(1024)
                var size = 0
                do{
                    if(size>=0) {
                        fos.write(buffer, 0, size)
                    }
                    size = `is`!!.read(buffer, 0, 1024)
                }
                while (size >= 0)
                isCopyed = true
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } finally {
                        `is`?.close()
                    }
                }
            }

        }
        return isCopyed
    }
}
