package com.mml.onceapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mml.onceapplication.R
import com.mml.onceapplication.dialog.*
import com.mml.onceapplication.showToast
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import androidx.annotation.NonNull
import com.baidu.speech.utils.Policy.app
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment


class BottomSheetActivity : AppCompatActivity() {
    private lateinit var mBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mml.onceapplication.R.layout.activity_bottom_sheet)
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
            val view = layoutInflater.inflate(com.mml.onceapplication.R.layout.bottom_sheet_dialog1, null)
            dialog.setContentView(view)
//            dialog.getWindow().findViewById(R.id.design_bottom_sheet)
//                .setBackgroundResource(android.R.color.transparent)
//            var contentView=dialog.requireViewById<Fragment>(R.id.design_bottom_sheet)//view.findViewById(com.mml.onceapplication.R.id.dialog_bottom_sheet) as NestedScrollView
//            var behavior = BottomSheetBehavior.from(contentView)
//            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    //这里是bottomSheet 状态的改变
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    //这里是拖拽中的回调，根据slideOffset可以做一些动画
//                }
//            })
//            behavior.peekHeight=40
//            behavior.isHideable = false //此处设置表示禁止BottomSheetBehavior的执行
            dialog.show()
          //  behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        easy_dialog.setOnClickListener {
/*            val dialog= CustomDialog()
                .setLayoutRes(R.layout.dialog_easy_sample)
                .setOnDismissCallback {  showToast("onDismissCallback") }
                .convert { it-> }
                .show(fragmentManager = supportFragmentManager)*/


            val sss=EasyDialog.init<CustomDialog>(EasyDialog.DialogType.CUSTOM)
                .setLayoutRes(com.mml.onceapplication.R.layout.dialog_easy_sample)
                .setOnDismissCallback {  showToast("onDismissCallback") }
                .convert { it-> }
                .show(fragmentManager = supportFragmentManager)
        }
        simple_dialog.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleEditTextDialog>(EasyDialog.DialogType.SIMPLEEDITTEXT).init(this)

                .show()
        }
        val item= arrayListOf<Any>(false,"2",3,"4","5")
        simple_dialog_list.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleListDialog>(EasyDialog.DialogType.LIST).init(this)
                .setItems(item)
                .setOnConfirmClickCallback {
                    showToast(it)
                }
                .show()
        }
        simple_dialog_single.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleSingleChoiceDialog>(EasyDialog.DialogType.SINGLECHOICE).init(this)
                .setItems(item)
                .setDefaultSelect(3)
                .setOnConfirmClickCallback {v,p->
                    showToast(v.toString())
                }
                .show()
        }
        simple_dialog_multi.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleMultiChoiceDialog>(EasyDialog.DialogType.MULTICHOICE).init(this)
                .setItems(item)
                .show()
        }

    }
}
