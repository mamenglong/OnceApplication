package com.mml.onceapplication.activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mml.onceapplication.R
import com.mml.onceapplication.utils.CodeUtils
import kotlinx.android.synthetic.main.activity_code.*


class CodeActivity : AppCompatActivity() {

    var getCode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        initView()
    }

    fun initView() {
        vc_image.setImageBitmap(CodeUtils.getInstance().bitmap)
        getCode = CodeUtils.getInstance().getCode(); // 获取显示的验证码
        vc_shuaixi.setOnClickListener { view ->
            vc_image.setImageBitmap(CodeUtils.getInstance().getBitmap())
            getCode = CodeUtils.getInstance().getCode()
        }
        vc_ok.setOnClickListener { v ->
            val v_code = vc_code.text.toString().trim()
            if (v_code == "") {
                Toast.makeText(this@CodeActivity, "验证码为空", Toast.LENGTH_SHORT).show()
            } else if (!v_code.equals(getCode, ignoreCase = true)) {
                Toast.makeText(this@CodeActivity, "验证码错误", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CodeActivity, "验证成功", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun View.gone(){
    visibility=View.GONE
}