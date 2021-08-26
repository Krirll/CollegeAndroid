package com.example.practicandroid9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddElementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_element)
        findViewById<Button>(R.id.AddElem).setOnClickListener {

        }
    }
}