package com.mml.onceapplication.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mml.onceapplication.R
import com.mml.onceapplication.utils.MySpeechRecognizer
import com.mml.onceapplication.utils.MyWakeUp
import com.mml.onceapplication.utils.shortToast
import kotlinx.android.synthetic.main.activity_voice.*


class VoiceActivity : AppCompatActivity() {
    private lateinit var wakeUp:MyWakeUp
    private lateinit var speechRecognizer:MySpeechRecognizer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        initView()
        wakeUp = MyWakeUp(applicationContext)
        speechRecognizer = MySpeechRecognizer(applicationContext)
    }

    private val map = mapOf(
        Pair("开启识别", "s"),
        Pair("关闭识别", "s"),
        Pair("开启唤醒", ""),
        Pair("唤醒关闭", "s")
    )

    fun initView() {
        test.setOnClickListener {

        }
        map.forEach {
            val btn: Button = Button(this)
            val pair = Pair(it.key, it.value)

            btn.text = pair.first
            when (pair.first) {
                "开启识别" -> {
                    btn.setOnClickListener {
                        shortToast("开始识别", this@VoiceActivity)
                        speechRecognizer.setCallBack(object : MySpeechRecognizer.SpeechRecognizerCallBack {
                            override fun getResult(result: String) {
                                Log.i("识别结果", result)
                                shortToast(result, this@VoiceActivity)
                            }
                        })
                        speechRecognizer.Start()
                    }
                }
                "关闭识别" -> {
                    btn.setOnClickListener {
                        speechRecognizer.Stop()
                        shortToast("识别已关闭", this@VoiceActivity)
                    }
                }
                "开启唤醒" -> {
                    btn.setOnClickListener {
                        wakeUp.start()
                        shortToast("唤醒开启", this@VoiceActivity)
                    }
                }
                "唤醒关闭" -> {
                    btn.setOnClickListener {
                        wakeUp.stop()
                        shortToast("唤醒关闭", this@VoiceActivity)
                    }
                }
            }
            main_view.addView(btn)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.Destroy()
        wakeUp.release()
    }
}
