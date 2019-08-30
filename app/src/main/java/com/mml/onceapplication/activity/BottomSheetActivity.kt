package com.mml.onceapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mml.onceapplication.R
import com.mml.onceapplication.dialog.EasyDialog
import com.mml.onceapplication.showToast
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*


class BottomSheetActivity : AppCompatActivity() {
    private lateinit var mBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        initView()
    }

    private fun initView() {
        mBehavior = BottomSheetBehavior.from(bottom_sheet)
        mBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //这里是bottomSheet状态的改变
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        })
        show_bottom_sheet.setOnClickListener { view ->
            if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            } else {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }
        show_bottom_sheet_dialog.setOnClickListener { view->
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            dialog.setContentView(view)
//            var contenView=view.findViewById(R.id.dialog_bottom_sheet) as  NestedScrollView
//            var behavior = BottomSheetBehavior.from(contenView)
//            behavior.isHideable = false //此处设置表示禁止BottomSheetBehavior的执行
            dialog.show()
        }
        easy_dialog.setOnClickListener {
            val dialog= EasyDialog()
                .setLayoutRes(R.layout.dialog_easy_sample)
                .setOnDismissCallback {  showToast("onDismissCallback") }
                .convert { it-> }
                .show(fragmentManager = supportFragmentManager)
        }
    }
}
