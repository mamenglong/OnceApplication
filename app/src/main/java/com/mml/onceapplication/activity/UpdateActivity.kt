package com.mml.onceapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.onceapplication.R
import com.mml.updatelibrary.service.UpdateService
import com.mml.updatelibrary.ui.UpdateUtil
import kotlinx.android.synthetic.main.activity_update1.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*

class UpdateActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update1)
        btn_update.setOnClickListener {
            UpdateUtil.checkUpdate()
        }
        btn_update_process.setOnClickListener {
            sendBroadcast(Intent(UpdateService.ACTION_UPDATE_PROCESS))
            
        }
        btn_update_Cancel_no_notice.setOnClickListener {
            UpdateUtil.cancelNoLongerRemind()
        }
    }
}
