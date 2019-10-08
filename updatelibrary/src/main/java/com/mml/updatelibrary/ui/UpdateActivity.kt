package com.mml.updatelibrary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.updatelibrary.GlobalContextProvider
import com.mml.updatelibrary.R
import com.mml.updatelibrary.Utils
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.log
import com.mml.updatelibrary.service.UpdateService
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    lateinit var updateInfo:UpdateInfo
    companion object{
        fun start() {
            log(msg = "content:start", tag = "UpdateActivity")

            val context = GlobalContextProvider.getGlobalContext().applicationContext
            val intent = Intent(context, UpdateActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        updateInfo=UpdateUtil.updateInfo
        log(msg = "content:$updateInfo", tag = "UpdateActivity")
        initUiView()
    }

    private fun initUiView() {
        tv_update_title.text=updateInfo.updateTitle
        tv_update_content.text=updateInfo.updateContent
        btn_update_sure.setOnClickListener {
             UpdateService.start(this)
        }
        btn_update_cancel.setOnClickListener {
            if (updateInfo.config.force){
                 Utils.exitApp()
            } else{
                finish()
            }
        }
    }

    override fun onBackPressed() {

    }
}
