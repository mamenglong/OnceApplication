package com.mml.onceapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.onceapplication.R
import com.mml.onceapplication.fragment.ViewBindFragment

class ViewBindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bindctivity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,ViewBindFragment.newInstance())
            .commitNow()
    }
}