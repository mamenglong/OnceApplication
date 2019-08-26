package com.mml.onceapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.mml.onceapplication.PagingActivity
import com.mml.onceapplication.R
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private val map = mutableMapOf(
        Pair("bottom_sheet", BottomSheetActivity::class.java),
        Pair("悬浮窗", FloatActivity::class.java),
        Pair("验证码", CodeActivity::class.java),
        Pair("语音识别", VoiceActivity::class.java),
        Pair("语音唤醒识别", WakeUpRecogActivity::class.java),
        Pair("地图", MapActivity::class.java),
        Pair("AppBar", AppBarActivity::class.java),

        Pair("天气", WeatherActivity::class.java) ,
        Pair("文字时钟",StageActivity::class.java),
        Pair("RoomTest",RoomTestActivity::class.java) ,
        Pair("RoomPageTest",PagingActivity::class.java)

    )
    private fun initView(){
        map.forEach {
            val button: Button = Button(this)
            button.text=it.key
            val cls=it.value
            button.setOnClickListener {
                val intent = Intent(this@MainActivity,cls)
                if(button.text.toString()=="天气"){
                    val optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this,
                            androidx.core.util.Pair(view1,"transitionName"))
                    startActivity(intent,optionsCompat.toBundle())
                    finish()
                    return@setOnClickListener
                }
                startActivity(intent)
            }
            linear.addView(button)
        }

    }
}

