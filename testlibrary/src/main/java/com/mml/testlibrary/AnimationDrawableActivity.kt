package com.mml.testlibrary

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AnimationDrawableActivity : AppCompatActivity() {
    private lateinit var rocketAnimation: AnimationDrawable
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_drawable)

        val rocketImage = findViewById<ImageView>(R.id.rocket_image).apply {
            setBackgroundResource(R.drawable.animation_drawable)
            rocketAnimation = background as AnimationDrawable
        }

        rocketImage.setOnClickListener { rocketAnimation.start() }
    }
}
