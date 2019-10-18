package com.mml.onceapplication.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.util.logging.Logger

/**
 * 项目名称：OnceApplication
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 17:01
 */
fun shortToast(msg:String,context: Context){
    val handler=context.mainLooper
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}

/**
 * 复制文件到SD卡
 * @param context
 * @param fileName 复制的文件名
 * @param path  保存的目录路径
 * @return
 */

fun copyAssetsFile(context: Context, fileName: String, path: String,onProgress:((msg:String,progress:Int,uri: Uri?)->Unit)?): Uri? {
    val mInputStream = context.assets.open(fileName)
    val file = File(path)
    if (!file.exists()) {
        file.mkdirs()
    }
    val apk = File(path + File.separator + "autojs.apk")
    if (!apk.exists())
        apk.createNewFile()
    val mFileOutputStream = FileOutputStream(apk)
    try {
        var read: Int = -1
        var local=0
        val all=mInputStream.available()
        mInputStream.use { input ->
            val byteArray= ByteArray(1024)
            mFileOutputStream.use {out->
                while (input.read(byteArray).also { read = it;local+=it} != -1) {
                    out.write(byteArray,0,read)
                    onProgress?.invoke("下载中...",local/all,null)
                }
            }
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        onProgress?.invoke("${t.cause}",-1,null)
    }
    var uri: Uri? = null
    try {
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //包名.fileprovider
            FileProvider.getUriForFile(context, "com.sadjoke.fake.fileprovider", apk)
        } else {
            Uri.fromFile(apk)
        }
    } catch (anfe: ActivityNotFoundException) {
       //  log(msg = anfe.message!!)
        onProgress?.invoke("${anfe.cause}",-1,null)
    }
    MediaScannerConnection.scanFile(context, arrayOf(apk.absolutePath), null, null)
    onProgress?.invoke("下载成功...",100,uri)
    return uri
}

/**
 * 安装apk
 *
 * @param context
 * @param uri
 */

fun installApk(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    val type = "application/vnd.android.package-archive"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } else {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    intent.setDataAndType(uri, type)
    context.startActivity(intent)
}