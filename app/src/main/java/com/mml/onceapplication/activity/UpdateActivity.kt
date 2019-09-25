package com.mml.onceapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.onceapplication.R
import com.mml.updatelibrary.service.UpdateService
import com.mml.updatelibrary.ui.UpdateDialog
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        btn_update.setOnClickListener {
            UpdateDialog().sss()
        }
        btn_update_process.setOnClickListener {
            sendBroadcast(Intent(UpdateService.ACTION_UPDATE_PROCESS))
            
        }
    }
}
