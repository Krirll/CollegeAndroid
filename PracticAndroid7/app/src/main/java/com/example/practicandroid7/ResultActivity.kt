package com.example.practicandroid7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        findViewById<TextView>(R.id.textView).text =
            intent.getStringExtra("RESULT")
    }
}