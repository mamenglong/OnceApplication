package com.mml.onceapplication

import android.app.Application

class OnceApplication: Application() {
    companion object{
        var instances:Application?=null
    }
    override fun onCreate() {
        super.onCreate()
       // LitePal.initialize(this)
        if (instances==null)
            instances=this
    }
}