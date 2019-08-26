package com.mml.onceapplication.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mml.onceapplication.R
import kotlinx.android.synthetic.main.activity_map.*
import java.io.File
import java.net.URISyntaxException



class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        gaode.setOnClickListener {
            gaode("洛阳","河南科技大学开元校区","","")
        }
    }

    val PACKEGE_GAODE = "com.autonavi.minimap"
    val PACKEGE_BAIDU = "com.baidu.BaiduMap"


    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private fun isInstallByread(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

    //跳转到地图软件进行导航,优先选用高德地图,其次用百度地图
    /**
     * 城市名 位置名  目的地纬度 目的地经度
     */
    private fun navigation(city: String, position: String, lat: String, lon: String) {
        try {
            var intent: Intent? = null
            if (isInstallByread(PACKEGE_GAODE)) {
                //高德地图导航的代码
                Toast.makeText(applicationContext, "打开高德地图", Toast.LENGTH_SHORT).show()
                intent = Intent.parseUri(
                    "androidamap://viewMap?sourceApplication=appname&poiname=$position&lat=$lat&lon=$lon&dev=0",
                    0
                )
            } else if (isInstallByread(PACKEGE_BAIDU)) {
                //百度地图导航的代码
                Toast.makeText(applicationContext, "打开百度地图", Toast.LENGTH_SHORT).show()
                intent = Intent.parseUri(
                    "intent://map/geocoder?location=$lat,$lon&coord_type=gcj02&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                    0
                )
            } else {
                Toast.makeText(applicationContext, "未安装高德或者百度地图软件", Toast.LENGTH_SHORT).show()
            }
            if (intent != null) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }

    private fun gaode(city: String, position: String, lat: String, lon: String){
        var intent: Intent? = null
        if (isInstallByread(PACKEGE_GAODE)) {
            //高德地图导航的代码
            Toast.makeText(applicationContext, "打开高德地图", Toast.LENGTH_SHORT).show()
            intent = Intent.parseUri(
                "androidamap://viewMap?sourceApplication=appname&poiname=$position&lat=$lat&lon=$lon&dev=0",
                0
            )
        }
        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
