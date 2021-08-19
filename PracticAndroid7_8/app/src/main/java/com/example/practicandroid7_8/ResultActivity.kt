package com.example.practicandroid7_8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        findViewById<TextView>(R.id.textView).text =
            intent.getStringExtra(resultEquation)
    }
    companion object {
        const val  resultEquation = "RESULT"
    }
}