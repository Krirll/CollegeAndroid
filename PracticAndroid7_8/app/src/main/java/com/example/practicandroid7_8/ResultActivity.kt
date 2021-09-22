package com.example.practicandroid7_8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val result = findViewById<TextView>(R.id.textView)
        result.text = intent.getStringExtra(resultEquation)
        findViewById<Button>(R.id.send).setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, result.text.toString())
                        type = "text/plain"
                    },
                    null
                )
            )
        }
    }
    companion object {
        const val  resultEquation = "RESULT"
    }
}