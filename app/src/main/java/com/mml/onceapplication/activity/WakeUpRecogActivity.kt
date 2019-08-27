package com.mml.onceapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Telephony.TextBasedSmsColumns.STATUS_NONE
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.baidu.speech.asr.SpeechConstant
import com.mml.core.voice.core.recog.IStatus
import com.mml.core.voice.core.recog.IStatus.Companion.STATUS_WAKEUP_SUCCESS
import com.mml.core.voice.core.recog.MyRecognizer
import com.mml.core.voice.core.recog.listener.MessageStatusRecogListener
import com.mml.core.voice.core.wakeup.MyWakeup
import com.mml.core.voice.core.wakeup.RecogWakeupListener
import com.mml.onceapplication.R
import com.mml.onceapplication.utils.shortToast
import kotlinx.android.synthetic.main.activity_wake_up_recog.*
import java.util.*

class WakeUpRecogActivity : AppCompatActivity() {
    private val map = mapOf(
        Pair("唤醒并识别", "s"),
        Pair("取消唤醒并识别", "s")
    )
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    private lateinit var myRecognizer: MyRecognizer
    /**
     * 0: 方案1， backTrackInMs > 0,唤醒词说完后，直接接句子，中间没有停顿。
     * 开启回溯，连同唤醒词一起整句识别。推荐4个字 1500ms
     * backTrackInMs 最大 15000，即15s
     *
     * >0 : 方案2：backTrackInMs = 0，唤醒词说完后，中间有停顿。
     * 不开启回溯。唤醒词识别回调后，正常开启识别。
     *
     *
     *
     */
    private val backTrackInMs = 1500
    var handler: Handler? = null
    private var myWakeup: MyWakeup? = null
    private var status = STATUS_NONE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up_recog)
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                handleMsg(msg!!)
            }
        }

        val recogListener = MessageStatusRecogListener(handler)
        // 改为 SimpleWakeupListener 后，不依赖handler，但将不会在UI界面上显示
        myRecognizer = MyRecognizer(this, recogListener)

        val listener = RecogWakeupListener(handler!!)
        myWakeup = MyWakeup(this, listener)
        initView()

    }

    fun initView() {
        map.forEach {
            val btn: Button = Button(this)
            val pair = Pair(it.key, it.value)
            btn.text = pair.first
            when (pair.first) {
                "唤醒并识别" -> {
                    btn.setOnClickListener {
                        refreshtextView("唤醒并识别")
                        start()
                    }
                }
                "取消唤醒并识别" -> {
                    btn.setOnClickListener {
                        refreshtextView("取消唤醒并识别")
                        stop()
                    }
                }
            }
            main_view.addView(btn)
        }
    }

    // 点击“开始识别”按钮
    // 基于DEMO唤醒词集成第2.1, 2.2 发送开始事件开始唤醒
    private fun start() {
        val params = HashMap<String, Any>()
        params[SpeechConstant.WP_WORDS_FILE] = "assets://WakeUp.bin"
        // "assets://WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
        params[com.baidu.speech.asr.SpeechConstant.APP_ID] = "15971505"
        // params.put(SpeechConstant.ACCEPT_AUDIO_DATA,true);
        // params.put(SpeechConstant.IN_FILE,"res:///com/baidu/android/voicedemo/wakeup.pcm");
        // params里 "assets://WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
        myWakeup?.start(params)
    }

    // 基于DEMO唤醒词集成第4.1 发送停止事件
    private fun stop() {
        myWakeup?.stop()
    }

    private fun handleMsg(msg: Message) {
        when (msg.what) {
            // 唤醒词识别成功的回调，见RecogWakeupListener
            // 此处 开始正常识别流程
            STATUS_WAKEUP_SUCCESS -> {
                refreshtextView("唤醒成功开始识别")
                val params = LinkedHashMap<String, Any>()
                params[SpeechConstant.ACCEPT_AUDIO_VOLUME] = false
                params[SpeechConstant.VAD] = SpeechConstant.VAD_DNN
                // 如识别短句，不需要需要逗号，使用1536搜索模型。其它PID参数请看文档
                params[SpeechConstant.PID] = 1536
                // 方案1  唤醒词说完后，直接接句子，中间没有停顿。开启回溯，连同唤醒词一起整句识别。
                // System.currentTimeMillis() - backTrackInMs ,  表示识别从backTrackInMs毫秒前开始

                params[SpeechConstant.AUDIO_MILLS] = System.currentTimeMillis() - backTrackInMs
                myRecognizer.cancel()
                myRecognizer.start(params)
            }
            10001 -> {
                refreshtextView("唤醒失败")
                shortToast(msg.obj as String, this@WakeUpRecogActivity)
            }
            IStatus.WHAT_MESSAGE_STATUS -> {
                refreshtextView(msg.obj.toString())
            }
            IStatus.STATUS_FINISHED -> {
                refreshtextView(msg.obj.toString())
            }
            else -> {
                refreshtextView(msg.obj.toString())
            }
        }
    }

    private fun refreshtextView(msg: String) {
        text.append(msg + "\n")
        Log.i("height","text.height:${text.height} ${text.lineCount*text.lineHeight} ${text.lineCount} ${text.lineHeight}  main_view.height:${main_view.height}")
        scrollView.scrollTo(0,text.height+main_view.height)
    }

    override fun onDestroy() {
        // 基于DEMO唤醒词集成第5 退出事件管理器
        myWakeup?.release()
        myRecognizer.release()
        super.onDestroy()
    }

}
