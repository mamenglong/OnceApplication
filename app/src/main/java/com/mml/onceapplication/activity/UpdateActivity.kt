package com.mml.onceapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.onceapplication.R
import com.mml.updatelibrary.ui.UpdateDialog
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        btn_update.setOnClickListener {
             UpdateDialog().checkUpdate()
        }
    }
}
