package com.mml.onceapplication.dialog

import java.lang.IllegalArgumentException

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 上午10:03
 * Description: This is EasyDialog
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
class EasyDialog {
    enum class DialogType {
        SIMPLE,
        CUSTOM,
        LIST
    }
    companion object{
       @JvmStatic
       fun<T> init(type:DialogType):T {
           return when(type){
               DialogType.CUSTOM->{
                   CustomDialog() as T
               }
               DialogType.SIMPLE->{
                   SimpleDialog()  as T
               }
               DialogType.LIST->{
                   SimpleDialog() as T
               }
           }
       }
    }
}