package com.mml.onceapplication.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mml.onceapplication.R
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.Unit
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.view.HeConfig
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import kotlinx.android.synthetic.main.activity_weather.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//        window.allowEnterTransitionOverlap = false
//        window.allowReturnTransitionOverlap = false


        //getWindow().setSharedElementEnterTransition(enterTransition());
        //getWindow().setSharedElementReturnTransition(returnTransition());
//        window.sharedElementEnterTransition = null
//        window.sharedElementReturnTransition = null
        setContentView(R.layout.activity_weather)
        HeConfig.init("HE1904091415011817", "667a487e8aed4b2db8948c46e9337366")
        HeConfig.switchToFreeServerNode()
        getWeatherWithPermissionCheck()
    }
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    fun getWeather() {
        /**
         * 实况天气
         * 实况天气即为当前时间点的天气状况以及温湿风压等气象指数，具体包含的数据：体感温度、
         * 实测温度、天气状况、风力、风速、风向、相对湿度、大气压强、降水量、能见度等。
         *
         * @param context  上下文
         * @param location 地址详解
         * @param lang       多语言，默认为简体中文
         * @param unit        单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener  网络访问回调接口
         */
        HeWeather.getWeatherNow(this, null, Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
            object : HeWeather.OnResultWeatherNowBeanListener {
                override fun onError(e: Throwable) {
                    Log.i("Log", "onError: ", e)
                }

                override fun onSuccess(dataObject: List<Now>) {
                    text.text = Gson().toJson(dataObject)
                    Log.i("Log", "onSuccess: " + Gson().toJson(dataObject))
                }
            })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }


}
