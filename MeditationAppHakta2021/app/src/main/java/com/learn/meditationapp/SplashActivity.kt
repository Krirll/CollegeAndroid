package com.learn.meditationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        //TODO переход с помощью логотипа
        Handler().postDelayed({
            //start "hello" activity
            startActivity(Intent(this, HelloActivity::class.java))
            finish()
        },1500)
    }
}