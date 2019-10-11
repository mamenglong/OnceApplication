package com.mml.updatelibrary.ui

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.WindowManager
import com.mml.easyconfig.AndroidConfig
import com.mml.updatelibrary.GlobalContextProvider
import com.mml.updatelibrary.R
import com.mml.updatelibrary.Utils
import com.mml.updatelibrary.data.SP
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.log
import com.mml.updatelibrary.service.UpdateService
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    lateinit var updateInfo: UpdateInfo

    companion object {
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
        initUi()
        updateInfo = UpdateUtil.updateInfo
        log(msg = "content:$updateInfo", tag = "UpdateActivity")
        initView()
        initConfig()
    }

    private fun initConfig() {
          if (updateInfo.config.force){
              btn_update_cancel.visibility= View.GONE
          }else{
              if (SP.ignoreVersion < updateInfo.config.serverVersionCode){
                  btn_update_cancel.visibility= View.VISIBLE
              }else{
                  btn_update_cancel.visibility= View.GONE
                  finish()
              }
          }
    }

    private fun initUi() {
        val d = windowManager.defaultDisplay // 为获取屏幕宽、高
        val p = window.attributes
        val point = Point()
        d.getSize(point)
        p.height = ((point.y * 0.4).toInt()) // 高度设置为屏幕的0.3
        p.width = ((point.x * 0.7).toInt()) // 宽度设置为屏幕的0.7
        window.attributes = p
    }

    private fun initView() {
        if (updateInfo.updateTitle.isNotEmpty())
            tv_update_title.text = updateInfo.updateTitle
        if (updateInfo.updateContent.isNotEmpty())
            tv_update_content.text = updateInfo.updateContent
        btn_update_sure.setOnClickListener {
            UpdateService.start(this)
        }
        btn_update_cancel.setOnClickListener {
            SP.ignoreVersion=updateInfo.config.serverVersionCode
            finish()
        }
    }

    override fun onBackPressed() {
        if (updateInfo.config.force) {
            Utils.exitApp()
        } else {
          super.onBackPressed()
        }
    }
}
