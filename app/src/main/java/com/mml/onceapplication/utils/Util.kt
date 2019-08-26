package com.mml.onceapplication.utils

import android.content.Context
import android.widget.Toast

/**
 * 项目名称：OnceApplication
 * Created by Long on 2019/4/9.
 * 修改时间：2019/4/9 17:01
 */
fun shortToast(msg:String,context: Context){
    val handler=context.mainLooper
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}