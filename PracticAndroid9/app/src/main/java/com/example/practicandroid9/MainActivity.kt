package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val add = findViewById<Button>(R.id.AddButton)
        val show = findViewById<Button>(R.id.ShowButton)
        add.setOnClickListener {
            val intent = Intent(this, AddEditElementActivity::class.java)
            startActivity(intent)
        }
        show.setOnClickListener {
            val intent = Intent(this, ShowElementsActivity::class.java)
            startActivity(intent)
        }
    }
}