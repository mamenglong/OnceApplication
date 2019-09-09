package com.mml.onceapplication.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mml.onceapplication.R
import com.mml.onceapplication.service.FloatWindowService
import kotlinx.android.synthetic.main.activity_float_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class FloatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_main)
        start_float_window.setOnClickListener {
            startServiceWithPermissionCheck()
        }
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onActivityResult(requestCode)
    }

    @NeedsPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
    fun startService(){
        val intent = Intent(this@FloatActivity, FloatWindowService::class.java)
        startService(intent)
        finish()
    }

}
